package com.oc_p9.diabetes_assessment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.oc_p9.diabetes_assessment_service.dto.PatientDto;

/**
 * Client Feign pour communiquer avec le microservice des patients.
 * Ce client permet de récupérer les informations d'un patient.
 */
@FeignClient(name = "patient-service", url = "${patient.url}")
public interface PatientClient {

    /**
     * Récupère les informations d'un patient à partir de son identifiant.
     *
     * @param id L'identifiant du patient
     * @param authorization Le token JWT à inclure dans l'en-tête pour l'authentification
     * @return Un objet contenant les données du patient
     */
    @GetMapping("{id}")
    PatientDto getPatientById(@PathVariable("id") int id, @RequestHeader("Authorization") String authorization);

}
