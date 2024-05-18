package com.example.designpatterns.exception;

public class InvoiceGenerationFailedException extends RuntimeException {
    public InvoiceGenerationFailedException(String message) {super(message);}
}
