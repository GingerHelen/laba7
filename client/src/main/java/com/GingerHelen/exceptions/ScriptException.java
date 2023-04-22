package com.GingerHelen.exceptions;

public class ScriptException extends Exception {
    public final String message;

    public ScriptException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

