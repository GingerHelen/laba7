package com.GingerHelen.client.exceptions;

public class IllegalValueException extends Exception {
    public final String message;
    public IllegalValueException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
