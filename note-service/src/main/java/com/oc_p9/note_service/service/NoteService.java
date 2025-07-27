package com.oc_p9.note_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oc_p9.note_service.exception.NoteNotFoundException;
import com.oc_p9.note_service.model.Note;
import com.oc_p9.note_service.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service pour gérer les opérations liées aux notes.
 */
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Retourne la liste de toutes les notes.
     * 
     * @return liste de toutes les notes
     */
    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    /**
     * Trouve toutes les notes d'un patient grâce à son ID.
     * 
     * @param patId l'ID du patient
     * @return liste des notes du patient
     * @throws NoteNotFoundException si aucune note n'est trouvée pour ce patient
     */
    public List<Note> findByPatId(int patId) {
        List<Note> notes = noteRepository.findByPatId(patId);
        if (notes.isEmpty()) {
            throw new NoteNotFoundException("Aucune note trouvée pour le patient ID : " + patId);
        }
        return notes;
    }

    /**
     * Enregistre une nouvelle note ou met à jour une note existante.
     * 
     * @param note la note à enregistrer
     * @return la note enregistrée
     */
    public Note save(Note note) {
        return noteRepository.save(note);
    }
}
