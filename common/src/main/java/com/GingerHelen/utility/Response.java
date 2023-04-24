package com.GingerHelen.utility;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private final ResponseCode responseCode;

    public Response(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Response(ResponseCode responseCode, String message) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
