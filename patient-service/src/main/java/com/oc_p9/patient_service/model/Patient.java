package com.oc_p9.patient_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_de_naissance", nullable = false)
    private LocalDate dateDeNaissance;

    @Column(name = "genre")
    private String genre;

    @Column(name = "adresse")
    private String adressePostale;

    @Column(name = "telephone")
    private String telephone;
}
