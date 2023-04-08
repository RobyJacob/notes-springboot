package com.example.notes.controllers;

import com.example.notes.dtos.CreateNoteDTO;
import com.example.notes.dtos.NoteResponseDTO;
import com.example.notes.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {
    NoteService noteService;

    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes(
            @RequestParam(value = "title_pre", required = false) String titlePrefix) {
        if (Objects.nonNull(titlePrefix)) {
            return ResponseEntity.ok(noteService.getAllNotesByTitlePrefix(titlePrefix));
        }

        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{note_id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable("note_id") Integer noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@RequestBody CreateNoteDTO createNoteDTO) {
        var savedNote = noteService.addNote(createNoteDTO);

        return ResponseEntity.created(URI.create("/notes/" + savedNote.getId()))
                .body(savedNote);
    }

    @DeleteMapping("/{note_id}")
    public ResponseEntity<String> deleteNote(@PathVariable("note_id") Integer noteId) {
        noteService.deleteNote(noteId);

        return ResponseEntity.accepted().body("Note successfully deleted");
    }

    @PatchMapping("/{note_id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable("note_id") Integer noteId,
                                                      @RequestBody CreateNoteDTO newNote) {
        var updatedNote = noteService.updateNote(noteId, newNote);

        return ResponseEntity.accepted().body(updatedNote);
    }
}
