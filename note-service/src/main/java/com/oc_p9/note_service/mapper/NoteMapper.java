package com.oc_p9.note_service.mapper;

import org.mapstruct.Mapper;

import com.oc_p9.note_service.dto.NoteDTO;
import com.oc_p9.note_service.model.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDTO toDto(Note note);

    Note toEntity(NoteDTO dto);

}
