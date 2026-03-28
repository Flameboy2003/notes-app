package com.example.Notes.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;
    private Date createdAt;
    private Date updateAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updateAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }



    // Each note belongs to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
