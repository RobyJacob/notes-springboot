package com.example.notes.controllers;

import com.example.notes.dtos.CreateNoteDTO.CreateNoteDTO;
import com.example.notes.dtos.NoteResponseDTO;
import com.example.notes.services.NoteService.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {
    NoteService noteService;

    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        return ResponseEntity.ok()
                .body(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@RequestBody CreateNoteDTO createNoteDTO) {
        var savedNote = noteService.addNote(createNoteDTO);

        return ResponseEntity.created(URI.create("/notes/" + savedNote.getId()))
                .body(savedNote);
    }
}
