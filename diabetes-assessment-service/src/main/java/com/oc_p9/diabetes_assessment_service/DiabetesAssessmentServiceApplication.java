package com.oc_p9.diabetes_assessment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.oc_p9.diabetes_assessment_service.feign")
public class DiabetesAssessmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabetesAssessmentServiceApplication.class, args);
	}

}
