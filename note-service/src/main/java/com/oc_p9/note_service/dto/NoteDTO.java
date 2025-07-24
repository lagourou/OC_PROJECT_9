package com.oc_p9.note_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteDTO {

    private String id;

    @NotNull
    private int patId;

    @NotBlank
    private String patient;

    @NotBlank
    private String note;

}
