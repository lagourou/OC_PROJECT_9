package com.OC_P9.medilabo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.OC_P9.medilabo.model.Patient;
import com.OC_P9.medilabo.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatient());
    }

    @PutMapping(value = "/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @Valid @RequestBody Patient updatePatient) {

        return patientService.updatePatient(id, updatePatient)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }
}
