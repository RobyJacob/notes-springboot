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
        if (Objects.isNull(createNoteDTO))
            throw new IllegalArgumentException("Note is required");

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
        if (Objects.isNull(noteId))
            throw new IllegalArgumentException("Note Id cannot be null");
        if (!noteRepository.existsById(noteId))
            throw new NoteNotFoundException("Note with id: " + noteId + " does not exist");

        noteRepository.deleteById(noteId);
    }

    public NoteResponseDTO updateNote(Integer noteId, CreateNoteDTO newNote) {
        if (Objects.isNull(noteId) || Objects.isNull(newNote))
            throw new IllegalArgumentException("Id and note to update is required");

        var note = getNoteById(noteId);

        if (Objects.isNull(note))
            throw new NoteNotFoundException("Note with id: " + noteId + " does not exist");

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
        if (Objects.isNull(noteId) || noteId.equals(0))
            throw new IllegalArgumentException("Id cannot be null or zero");

        var noteEntity = noteRepository.findById(noteId);

        if (noteEntity.isEmpty()) throw new NoteNotFoundException("Note with id: " + noteId
                + " does not exist");

        return modelMapper.map(noteEntity, NoteResponseDTO.class);
    }

    public List<NoteResponseDTO> getAllNotesByTitlePrefix(String noteTitlePrefix) {
        if (noteTitlePrefix.isEmpty())
            throw new IllegalArgumentException("Prefix cannot be empty or null");

        var notes = noteRepository.findByTitleStartsWithIgnoreCase(noteTitlePrefix);

        if (notes.isEmpty()) throw new NoteNotFoundException("Note does not exist");

        return notes.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }

    public static class NoteNotFoundException extends RuntimeException {
        public NoteNotFoundException(String message) {
            super(message);
        }
    }
}
