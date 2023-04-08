package com.example.notes.repositories;

import com.example.notes.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
    List<NoteEntity> findByTitleStartsWithIgnoreCase(String titlePrefix);
}
