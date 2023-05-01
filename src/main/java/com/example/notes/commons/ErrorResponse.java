package com.example.notes.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends RuntimeException {
    private String message;

    public ErrorResponse(String message) {
        super(message);
        setMessage(message);
    }

    public ErrorResponse() {
    }
}
