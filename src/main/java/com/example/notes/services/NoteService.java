package com.example.notes.services;

import com.example.notes.dtos.CreateNoteDTO;
import com.example.notes.dtos.NoteResponseDTO;
import com.example.notes.entities.NoteEntity;
import com.example.notes.repositories.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NoteService {
    NoteRepository noteRepository;

    ModelMapper modelMapper;

    public NoteService(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    public NoteResponseDTO addNote(CreateNoteDTO createNoteDTO) {
        var noteEntity = modelMapper.map(createNoteDTO, NoteEntity.class);

        var savedNote = noteRepository.save(noteEntity);

        return modelMapper.map(savedNote, NoteResponseDTO.class);
    }

    public List<NoteResponseDTO> getAllNotes() {
        var noteEntites = noteRepository.findAll();

        return noteEntites.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }

    public void deleteNote(Integer noteId) {
        noteRepository.deleteById(noteId);
    }

    public NoteResponseDTO updateNote(Integer noteId, CreateNoteDTO newNote) {
        var note = getNoteById(noteId);

        if (Objects.nonNull(newNote.getTitle())) {
            note.setTitle(newNote.getTitle());
        }
        if (Objects.nonNull(newNote.getBody())) {
            note.setBody(newNote.getBody());
        }

        noteRepository.save(modelMapper.map(note, NoteEntity.class));

        return note;
    }

    public NoteResponseDTO getNoteById(Integer noteId) {
        var noteEntity = noteRepository.findById(noteId);

        return modelMapper.map(noteEntity, NoteResponseDTO.class);
    }

    public List<NoteResponseDTO> getAllNotesByTitlePrefix(String noteTitlePrefix) {
        var notes = noteRepository.findByTitleStartsWithIgnoreCase(noteTitlePrefix);

        return notes.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }
}
