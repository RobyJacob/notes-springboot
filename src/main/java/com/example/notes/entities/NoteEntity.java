package com.example.notes.entities;

import com.example.notes.commons.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "notes")
@Getter
@Setter
public class NoteEntity extends BaseEntity {
    private String title;

    private String body;
}
