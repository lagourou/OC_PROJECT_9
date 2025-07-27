package com.oc_p9.diabetes_assessment_service.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oc_p9.diabetes_assessment_service.dto.AssessmentDto;
import com.oc_p9.diabetes_assessment_service.dto.NoteDto;
import com.oc_p9.diabetes_assessment_service.dto.PatientDto;
import com.oc_p9.diabetes_assessment_service.exception.PatientNotFoundException;
import com.oc_p9.diabetes_assessment_service.exception.UnauthorizedAccessException;
import com.oc_p9.diabetes_assessment_service.feign.NoteClient;
import com.oc_p9.diabetes_assessment_service.feign.PatientClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

/**
 * Service qui permet d’évaluer le risque de diabète d’un patient.
 * Il récupère les données du patient et ses notes médicales,
 * puis détermine le niveau de risque selon son âge, son genre et des mots-clés trouvés dans ses notes.
 */
@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    /**
     * Évalue le niveau de risque de diabète d’un patient selon son âge, son genre
     * et les termes présents dans ses notes médicales.
     *
     * @param patId L'identifiant du patient
     * @param authorization Le token JWT pour vérifier les droits d’accès
     * @return Un Dto avec l’ID du patient et le niveau de risque
     * @throws UnauthorizedAccessException Si le token est manquant ou invalide
     * @throws PatientNotFoundException Si le patient n’existe pas
     * @throws RuntimeException En cas d’erreur avec les notes du patient
     */
    public AssessmentDto assessRisk(int patId, String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedAccessException("Token manquant ou invalide");
        }
        try {
            PatientDto patients = patientClient.getPatientById(patId, authorization);
            List<NoteDto> notes = noteClient.getNotesByPatientId(patId, authorization);

            int age = Period.between(patients.getDateDeNaissance(), LocalDate.now()).getYears();
            int triggerCount = countTriggers(notes);
            String riskLevel = determineRiskLevel(age, patients.getGenre(), triggerCount);

            AssessmentDto result = new AssessmentDto();
            result.setPatId(patId);
            result.setRiskLevel(riskLevel);
            return result;

        } catch (FeignException.NotFound ex) {
            throw new PatientNotFoundException("Aucun patient trouvé avec l'ID : " + patId);
        } catch (FeignException ex) {
            throw new RuntimeException("Erreur lors de la récupération des notes du patient : " + ex.getMessage());
        }
    }

    private static final List<String> TRIGGERS = List.of(
            "hémoglobine a1c", "microalbumine", "taille", "poids",
            "fumeur", "fumeuse", "anormal", "cholestérol",
            "vertiges", "rechute", "réaction", "anticorps");

    /**
     * Compte le nombre de mots-clés (triggers) présents dans les notes du patient.
     *
     * @param notes La liste des notes du patient
     * @return Le nombre de mots-clés trouvés
     */
    public int countTriggers(List<NoteDto> notes) {
        int count = 0;
        for (NoteDto note : notes) {
            String content = note.getNote().toLowerCase();
            for (String trigger : TRIGGERS) {
                if (content.contains(trigger)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Détermine le niveau de risque en fonction de l’âge, du genre et du nombre de mots-clés trouvés.
     *
     * @param age L’âge du patient
     * @param genre Le genre du patient ("M" ou "F")
     * @param triggerCount Le nombre de mots-clés trouvés
     * @return Le niveau de risque : "None", "Borderline", "In Danger" ou "Early onset"
     */
    private String determineRiskLevel(int age, String genre, int triggerCount) {
        String genres = genre.trim().toUpperCase();

        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return "Borderline";
        }

        if (age < 30) {
            if (genres.equals("F") && triggerCount >= 7) {
                return "Early onset";
            }
            if (genres.equals("F") && triggerCount >= 4) {
                return "In Danger";
            }
            if (genres.equals("M") && triggerCount >= 5) {
                return "Early onset";
            }
            if (genres.equals("M") && triggerCount >= 3) {
                return "In Danger";
            }
        }

        if (age > 30) {
            if (triggerCount == 6 || triggerCount == 7) {
                return "In Danger";
            }
            if (triggerCount >= 8) {
                return "Early onset";
            }
        }

        return "None";
    }
}
