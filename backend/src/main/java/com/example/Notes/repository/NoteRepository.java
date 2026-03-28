package com.example.Notes.repository;

import com.example.Notes.entity.Note;
import com.example.Notes.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    List<Note> findByUser(Users user);
    Optional<Note> findByIdAndUser(Long id, Users user);

}
