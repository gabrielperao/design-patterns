package com.example.designpatterns.exception;

public class ReservationCancellationFailedException extends RuntimeException {
    public ReservationCancellationFailedException(String message) {super(message);}
}
