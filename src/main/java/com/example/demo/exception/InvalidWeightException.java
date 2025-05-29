package com.example.demo.exception;

public class InvalidWeightException extends RuntimeException {
    public InvalidWeightException(String message) {
        super(message);
    }
}