package com.oc_p9.note_service.mapper;

import com.oc_p9.note_service.dto.NoteDTO;
import com.oc_p9.note_service.model.Note;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-27T07:53:06+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public NoteDTO toDto(Note note) {
        if ( note == null ) {
            return null;
        }

        NoteDTO noteDTO = new NoteDTO();

        noteDTO.setId( note.getId() );
        noteDTO.setPatId( note.getPatId() );
        noteDTO.setPatient( note.getPatient() );
        noteDTO.setNote( note.getNote() );

        return noteDTO;
    }

    @Override
    public Note toEntity(NoteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Note note = new Note();

        note.setId( dto.getId() );
        note.setPatId( dto.getPatId() );
        note.setPatient( dto.getPatient() );
        note.setNote( dto.getNote() );

        return note;
    }
}
