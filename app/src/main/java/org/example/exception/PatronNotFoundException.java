package org.example.exception;

public class PatronNotFoundException extends Exception {
    public PatronNotFoundException(String msg) {
        super(msg);
    }
}