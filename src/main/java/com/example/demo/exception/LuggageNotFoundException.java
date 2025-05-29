package com.example.demo.exception;

public class LuggageNotFoundException extends RuntimeException {
    public LuggageNotFoundException(String message) {
        super(message);
    }
}