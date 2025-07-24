package com.oc_p9.diabetes_assessment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.oc_p9.diabetes_assessment_service.dto.PatientDto;

@FeignClient(name = "patient-service", url = "${patient.url}")
public interface PatientClient {

    @GetMapping("{id}")
    PatientDto getPatientById(@PathVariable("id") int id, @RequestHeader("Authorization") String authorization);

}

