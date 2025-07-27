package com.oc_p9.patient_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oc_p9.patient_service.dto.PatientDTO;
import com.oc_p9.patient_service.mapper.PatientMapper;
import com.oc_p9.patient_service.model.Patient;
import com.oc_p9.patient_service.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
@Slf4j
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getPatients() {
        log.info("GET /api/patients hit!");
        List<PatientDTO> patientDto = patientService.getAllPatient().stream()
                .map(patientMapper::toDto)
                .toList();
        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatient(id);
        return ResponseEntity.ok(patientMapper.toDto(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable int id, @Valid @RequestBody PatientDTO dto) {
        Patient updated = patientService.updatedPatient(id, patientMapper.toEntity(dto));
        return ResponseEntity.ok(patientMapper.toDto(updated));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@Valid @RequestBody PatientDTO patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientMapper.toDto(savedPatient));
    }

}
