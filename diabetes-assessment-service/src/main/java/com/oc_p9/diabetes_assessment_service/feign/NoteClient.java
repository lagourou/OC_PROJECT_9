package com.oc_p9.diabetes_assessment_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.oc_p9.diabetes_assessment_service.dto.NoteDto;

@FeignClient(name = "note-service", url = "${note.url}")
public interface NoteClient {

    @GetMapping("{patId}")
    List<NoteDto> getNotesByPatientId(@PathVariable("patId") int patId,
            @RequestHeader("Authorization") String authorization);

}
