package com.example.demo.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class TaskInvalidDataException extends RuntimeException {
    public TaskInvalidDataException(String message) {
        super(message);
    }
}