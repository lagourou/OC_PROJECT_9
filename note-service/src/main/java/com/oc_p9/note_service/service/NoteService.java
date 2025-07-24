package com.oc_p9.note_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oc_p9.note_service.exception.NoteNotFoundException;
import com.oc_p9.note_service.model.Note;
import com.oc_p9.note_service.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    public List<Note> findByPatId(int patId) {
        List<Note> notes = noteRepository.findByPatId(patId);
        if (notes.isEmpty()) {
            throw new NoteNotFoundException("Aucune note trouvée pour le patient ID : " + patId);
        }
        return notes;
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(String id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException("Note introuvable avec l'ID : " + id);
        }
        noteRepository.deleteById(id);
    }

}
