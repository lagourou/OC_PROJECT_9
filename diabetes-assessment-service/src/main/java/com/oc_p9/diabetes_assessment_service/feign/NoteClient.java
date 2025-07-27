package com.oc_p9.diabetes_assessment_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.oc_p9.diabetes_assessment_service.dto.NoteDto;

/**
 * Client Feign pour communiquer avec le microservice des notes médicales.
 * Ce client permet de récupérer les notes associées à un patient.
 */
@FeignClient(name = "note-service", url = "${note.url}")
public interface NoteClient {

    /**
     * Récupère toutes les notes liées à un patient à partir de son identifiant.
     *
     * @param patId L'identifiant du patient
     * @param authorization Le token JWT à inclure dans l'en-tête pour l'authentification
     * @return Une liste d'objet correspondant aux notes du patient
     */
    @GetMapping("{patId}")
    List<NoteDto> getNotesByPatientId(@PathVariable("patId") int patId, @RequestHeader("Authorization") String authorization);

}
