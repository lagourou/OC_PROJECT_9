package com.oc_p9.patient_service.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientDTO {

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
