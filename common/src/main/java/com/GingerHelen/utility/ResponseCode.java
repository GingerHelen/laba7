package com.GingerHelen.utility;

public enum ResponseCode {
    OK("the command was completed"),
    ERROR("the command was not completed"),
    TEXT("server sent this response"),
    READ_SCRIPT("start read script"),
    EXIT("disconnect with server");

    private final String message;
    ResponseCode(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
