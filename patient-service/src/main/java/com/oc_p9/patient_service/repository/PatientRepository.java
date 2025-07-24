package com.oc_p9.patient_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oc_p9.patient_service.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
