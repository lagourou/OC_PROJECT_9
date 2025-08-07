package com.oc_p9.note_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oc_p9.note_service.dto.NoteDTO;
import com.oc_p9.note_service.mapper.NoteMapper;
import com.oc_p9.note_service.model.Note;
import com.oc_p9.note_service.service.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> notes = noteService.getAllNote().stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{patId}")
    public ResponseEntity<List<NoteDTO>> getNotesByPatientId(@PathVariable int patId) {
        List<NoteDTO> notes = noteService.findByPatId(patId).stream().map(noteMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> addNote(@Valid @RequestBody NoteDTO noteDTO) {
        Note note = noteMapper.toEntity(noteDTO);
        Note saveNote = noteService.save(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteMapper.toDto(saveNote));
    }

}
