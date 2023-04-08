package com.example.notes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoteDTO {
    private String title;

    private String body;
}
