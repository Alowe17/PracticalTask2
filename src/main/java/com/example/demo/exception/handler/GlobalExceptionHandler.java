package com.example.demo.exception.handler;

import com.example.demo.exception.custom.AccountInvalidDataException;
import com.example.demo.exception.custom.AccountNotFoundException;
import com.example.demo.exception.custom.TaskInvalidDataException;
import com.example.demo.exception.custom.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        log.error(ex.getMessage());
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(TaskInvalidDataException.class)
    public ResponseEntity<String> handleTaskInvalidDataException(TaskInvalidDataException ex) {
        log.error(ex.getMessage());
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AccountInvalidDataException.class)
    public ResponseEntity<String> handleAccountInvalidDataException(AccountInvalidDataException ex) {
        log.error(ex.getMessage());
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        log.error(ex.getMessage());
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}