package com.example.demo.exception.custom;

public class TaskInvalidDataException extends RuntimeException {
    public TaskInvalidDataException(String message) {
        super(message);
    }
}