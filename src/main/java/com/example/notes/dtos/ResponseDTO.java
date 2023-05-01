package com.example.notes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private String message;

    private String status;

    private Integer statusCode;

    private T responseObj;
}
