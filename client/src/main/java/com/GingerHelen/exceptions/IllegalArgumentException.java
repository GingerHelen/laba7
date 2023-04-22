package com.GingerHelen.exceptions;

public class IllegalArgumentException extends Exception {
    public final String message;
    public IllegalArgumentException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
