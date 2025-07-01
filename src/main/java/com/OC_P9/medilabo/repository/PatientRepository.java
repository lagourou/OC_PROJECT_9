package com.OC_P9.medilabo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.OC_P9.medilabo.model.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

}
