package com.example.designpatterns.exception;

public class TerminalException extends RuntimeException {
    public TerminalException(Throwable cause) {
        this.initCause(cause);
    }
}
