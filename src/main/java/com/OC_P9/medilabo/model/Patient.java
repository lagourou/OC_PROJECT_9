package com.OC_P9.medilabo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
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
