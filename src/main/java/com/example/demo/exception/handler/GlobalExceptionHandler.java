package com.example.demo.exception.handler;

import com.example.demo.exception.custom.AccountInvalidDataException;
import com.example.demo.exception.custom.AccountNotFoundException;
import com.example.demo.exception.custom.TaskInvalidDataException;
import com.example.demo.exception.custom.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(TaskInvalidDataException.class)
    public ResponseEntity<String> handleTaskInvalidDataException(TaskInvalidDataException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AccountInvalidDataException.class)
    public ResponseEntity<String> handleAccountInvalidDataException(AccountInvalidDataException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}