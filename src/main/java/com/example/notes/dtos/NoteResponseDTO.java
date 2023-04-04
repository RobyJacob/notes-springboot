package com.example.notes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteResponseDTO {
    private Integer id;

    private String title;

    private String body;
}
