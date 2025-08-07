package com.oc_p9.diabetes_assessment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oc_p9.diabetes_assessment_service.dto.AssessmentDto;
import com.oc_p9.diabetes_assessment_service.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @GetMapping("/{patId}")
    public ResponseEntity<AssessmentDto> getAssessment(
            @PathVariable int patId, @RequestHeader(value = "Authorization", required = false) String authorization) {

        AssessmentDto assessment = assessmentService.assessRisk(patId, authorization);
        return ResponseEntity.ok(assessment);
    }

}
