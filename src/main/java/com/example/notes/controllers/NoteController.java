package com.example.notes.controllers;

import com.example.notes.commons.ErrorResponse;
import com.example.notes.dtos.CreateNoteDTO;
import com.example.notes.dtos.NoteResponseDTO;
import com.example.notes.dtos.ResponseDTO;
import com.example.notes.services.NoteService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDTO<List<NoteResponseDTO>>> getAllNotes(
            @RequestParam(value = "title_pre", required = false) String titlePrefix) {
        ResponseDTO<List<NoteResponseDTO>> response = new ResponseDTO<>();

        if (Objects.nonNull(titlePrefix)) {
            response.setResponseObj(noteService.getAllNotesByTitlePrefix(titlePrefix));
        } else {
            response.setResponseObj(noteService.getAllNotes());
        }

        response.setStatus(HttpStatus.OK.name());
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{note_id}")
    public ResponseEntity<ResponseDTO<NoteResponseDTO>> getNoteById(@PathVariable("note_id")
                                                                        Integer noteId) {

        var note = noteService.getNoteById(noteId);

        ResponseDTO<NoteResponseDTO> response = new ResponseDTO<>();
        response.setResponseObj(note);
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<NoteResponseDTO>> addNote(@RequestBody
                                                                    CreateNoteDTO createNoteDTO) {
        var savedNote = noteService.addNote(createNoteDTO);

        ResponseDTO<NoteResponseDTO> response = new ResponseDTO<>();
        response.setResponseObj(savedNote);
        response.setStatus(HttpStatus.CREATED.name());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Saved successfully");

        return ResponseEntity.created(URI.create("/notes/" + savedNote.getId()))
                .body(response);
    }

    @DeleteMapping("/{note_id}")
    public ResponseEntity<ResponseDTO<String>> deleteNote(@PathVariable("note_id")
                                                 Integer noteId) {
        noteService.deleteNote(noteId);

        ResponseDTO<String> response = new ResponseDTO<>();
        response.setMessage("Successfully deleted");
        response.setStatusCode(HttpStatus.ACCEPTED.value());
        response.setStatus(HttpStatus.ACCEPTED.name());

        return ResponseEntity.accepted().body(response);
    }

    @PatchMapping("/{note_id}")
    public ResponseEntity<ResponseDTO<NoteResponseDTO>> updateNote(@PathVariable("note_id")
                                                                       Integer noteId,
                                                      @RequestBody CreateNoteDTO newNote) {
        var updatedNote = noteService.updateNote(noteId, newNote);

        ResponseDTO<NoteResponseDTO> response = new ResponseDTO<>();
        response.setResponseObj(updatedNote);
        response.setStatus(HttpStatus.ACCEPTED.name());
        response.setStatusCode(HttpStatus.ACCEPTED.value());
        response.setMessage("Successfully updated");

        return ResponseEntity.accepted().body(response);
    }

    @ExceptionHandler({
            NoteService.NoteNotFoundException.class,
            IllegalArgumentException.class,
            RuntimeException.class
    })
    public ResponseEntity<ResponseDTO<ErrorResponse>> errorHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        ResponseDTO<ErrorResponse> response = new ResponseDTO<>();
        response.setResponseObj(errorResponse);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setMessage(errorResponse.getMessage());

        return ResponseEntity.internalServerError()
                .body(response);
    }
}
