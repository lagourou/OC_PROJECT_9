package com.oc_p9.note_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.oc_p9.note_service.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatId(int patId);

}
