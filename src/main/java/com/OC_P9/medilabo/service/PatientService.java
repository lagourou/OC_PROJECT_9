package com.OC_P9.medilabo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.OC_P9.medilabo.model.Patient;
import com.OC_P9.medilabo.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();

    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);

    }

    public Optional<Patient> updatePatient(String id, Patient updatePatient) {
        if (!patientRepository.existsById(id)) {
            return Optional.empty();
        }
        updatePatient.setId(id);
        return Optional.of(patientRepository.save(updatePatient));
    }

}
