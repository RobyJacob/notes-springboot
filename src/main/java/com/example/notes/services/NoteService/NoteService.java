package com.example.notes.services.NoteService;

import com.example.notes.dtos.CreateNoteDTO.CreateNoteDTO;
import com.example.notes.dtos.NoteResponseDTO;
import com.example.notes.entities.NoteEntity;
import com.example.notes.repositories.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
