package com.example.Notes.services;

import com.example.Notes.dto.NoteRequest;
import com.example.Notes.dto.NoteResponse;
import com.example.Notes.entity.Note;
import com.example.Notes.entity.Users;
import com.example.Notes.repository.NoteRepository;
import com.example.Notes.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServices {


    private final NoteRepository noteRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public NoteServices(NoteRepository noteRepository, UsersRepository usersRepository) {
        this.noteRepository = noteRepository;
        this.usersRepository = usersRepository;
    }

    //Methods
    public NoteResponse createNote(NoteRequest noteRequest, String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = convertToEntity(noteRequest);
        note.setUser(user); //associating user and notes
        Note savedNote = noteRepository.save(note);

        return convertToResponseDto(savedNote);


    }

    private Note convertToEntity(NoteRequest noteRequest) {
        Note note = new Note();
        note.setText(noteRequest.getText());
        return note;
    }

    private NoteResponse convertToResponseDto(Note note) {

        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setCreatedAt(note.getCreatedAt());
        noteResponse.setUpdateAt(note.getUpdateAt());
        noteResponse.setText(note.getText());

        return noteResponse;
    }

    public List<NoteResponse> getAllNotes(String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Notes found "));
        List<Note> notes = noteRepository.findByUser(user);

        return notes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public NoteResponse updateNote(Long id, String newText, String name) {
        Users user = usersRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found exception"));
        Note note = noteRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setText(newText);
        noteRepository.save(note);
        return convertToResponseDto(note);

    }

    public void deleteNote(Long id, String name) {
        Users user = usersRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Note note = noteRepository.findByIdAndUser(id,user)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }
}
