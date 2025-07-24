package com.oc_p9.patient_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.oc_p9.patient_service.dto.PatientDTO;
import com.oc_p9.patient_service.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toEntity(PatientDTO dto);

    PatientDTO toDto(Patient entity);
}
