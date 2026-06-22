package com.example.demo.exception.custom;

public class AccountInvalidDataException extends RuntimeException {
    public AccountInvalidDataException(String message) {
        super(message);
    }
}
