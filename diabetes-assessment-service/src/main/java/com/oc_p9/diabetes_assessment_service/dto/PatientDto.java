package com.oc_p9.diabetes_assessment_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientDto {

    private String id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotNull
    private LocalDate dateDeNaissance;

    @NotBlank
    private String genre;

    private String adressePostale;

    private String telephone;

}
