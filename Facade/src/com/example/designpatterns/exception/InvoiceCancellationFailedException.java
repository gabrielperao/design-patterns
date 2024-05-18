package com.example.designpatterns.exception;

public class InvoiceCancellationFailedException extends RuntimeException {
    public InvoiceCancellationFailedException(String message) {super(message);}
}
