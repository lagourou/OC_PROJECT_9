package com.oc_p9.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oc_p9.patient_service.exception.PatientNotFoundException;
import com.oc_p9.patient_service.model.Patient;
import com.oc_p9.patient_service.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service pour gérer les opérations liées aux patients.
 */
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    /**
     * Retourne la liste de tous les patients.
     * 
     * @return liste de tous les patients
     */
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    /**
     * Retourne un patient grâce à son ID.
     * 
     * @param id l'ID du patient
     * @return le patient trouvé
     * @throws PatientNotFoundException si aucun patient n'est trouvé avec cet ID
     */
    public Patient getPatient(int id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Aucun patient trouvé avec l'ID : " + id));
    }

    /**
     * Enregistre un nouveau patient.
     * 
     * @param patient le patient à enregistrer
     * @return le patient enregistré
     */
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Met à jour un patient existant grâce à son ID.
     * 
     * @param id l'ID du patient à mettre à jour
     * @param updatePatient l'objet patient avec les nouvelles informations
     * @return le patient mis à jour
     * @throws PatientNotFoundException si le patient n'existe pas
     */
    public Patient updatedPatient(int id, Patient updatePatient) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Impossible de mettre à jour : patient introuvable avec l'ID : " + id);
        }
        updatePatient.setId(id);
        return patientRepository.save(updatePatient);
    }

}
