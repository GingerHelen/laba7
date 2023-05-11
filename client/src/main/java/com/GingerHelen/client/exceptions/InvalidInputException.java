package com.GingerHelen.client.exceptions;

public class InvalidInputException extends Exception {
    public String getMessage() {
        return "invalid input, work with the collection will be finished";
    }
}
