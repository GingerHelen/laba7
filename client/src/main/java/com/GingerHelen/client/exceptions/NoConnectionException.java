package com.GingerHelen.client.exceptions;

public class NoConnectionException  extends Exception {
    public String getMessage() {
        return "server is unavailable";
    }
}
