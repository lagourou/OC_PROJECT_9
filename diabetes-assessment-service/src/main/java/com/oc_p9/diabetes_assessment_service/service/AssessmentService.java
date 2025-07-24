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

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

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
