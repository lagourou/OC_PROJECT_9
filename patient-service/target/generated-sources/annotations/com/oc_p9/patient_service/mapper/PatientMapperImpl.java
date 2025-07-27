package com.oc_p9.patient_service.mapper;

import com.oc_p9.patient_service.dto.PatientDTO;
import com.oc_p9.patient_service.model.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-27T07:56:31+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient toEntity(PatientDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Patient patient = new Patient();

        if ( dto.getId() != null ) {
            patient.setId( Integer.parseInt( dto.getId() ) );
        }
        patient.setNom( dto.getNom() );
        patient.setPrenom( dto.getPrenom() );
        patient.setDateDeNaissance( dto.getDateDeNaissance() );
        patient.setGenre( dto.getGenre() );
        patient.setAdressePostale( dto.getAdressePostale() );
        patient.setTelephone( dto.getTelephone() );

        return patient;
    }

    @Override
    public PatientDTO toDto(Patient entity) {
        if ( entity == null ) {
            return null;
        }

        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setId( String.valueOf( entity.getId() ) );
        patientDTO.setNom( entity.getNom() );
        patientDTO.setPrenom( entity.getPrenom() );
        patientDTO.setDateDeNaissance( entity.getDateDeNaissance() );
        patientDTO.setGenre( entity.getGenre() );
        patientDTO.setAdressePostale( entity.getAdressePostale() );
        patientDTO.setTelephone( entity.getTelephone() );

        return patientDTO;
    }
}
