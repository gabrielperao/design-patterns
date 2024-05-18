package com.example.designpatterns.exception;

public class RefundProcessingFailedException extends RuntimeException {
    public RefundProcessingFailedException(String message) {super(message);}
}
