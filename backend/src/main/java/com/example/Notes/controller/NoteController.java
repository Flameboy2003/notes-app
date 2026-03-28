package com.example.Notes.controller;

import com.example.Notes.dto.NoteRequest;
import com.example.Notes.dto.NoteResponse;
import com.example.Notes.repository.NoteRepository;
import com.example.Notes.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final NoteServices noteServices;

    @Autowired
    public NoteController(NoteRepository noteRepository, NoteServices noteServices) {
        this.noteRepository = noteRepository;
        this.noteServices = noteServices;
    }


    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @RequestBody NoteRequest req,
            Principal principal
    ) {
        String email = principal.getName();
        NoteResponse res = noteServices.createNote(req, email);
        return ResponseEntity.ok(res);
    }


    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes(Principal principal) {
        String email = principal.getName();
        List<NoteResponse> noteResponses = noteServices.getAllNotes(email);
        return ResponseEntity.ok(noteResponses);

    }

    @PatchMapping("/{id}")
    public NoteResponse updateNote(@PathVariable Long id, @RequestBody NoteRequest request, Principal principal) {
        return noteServices.updateNote(id, request.getText(), principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long id,
            Principal principal
    ) {
        noteServices.deleteNote(id, principal.getName());
        return ResponseEntity.noContent().build();
    }


}
