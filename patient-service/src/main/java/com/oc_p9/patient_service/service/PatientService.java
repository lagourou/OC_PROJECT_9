package com.oc_p9.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oc_p9.patient_service.exception.PatientNotFoundException;
import com.oc_p9.patient_service.model.Patient;
import com.oc_p9.patient_service.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();

    }

    public Patient getPatient(int id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Aucun patient trouvé avec l'ID : " + id));
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);

    }

    public Patient updatedPatient(int id, Patient updatePatient) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Impossible de mettre à jour : patient introuvable avec l'ID : " + id);
        }
        updatePatient.setId(id);
        return patientRepository.save(updatePatient);
    }

    public void deletedPatient(int id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Impossible de supprimer : patient introuvable avec l'ID : " + id);
        }
        patientRepository.deleteById(id);

    }

}
