package com.GingerHelen.common.utility;

import lombok.Getter;

@Getter
public enum AuthorizationCode {
    AUTHORIZATION("You are logged in to your account"),
    REGISTRATION("You have been registered"),
    ERROR("Incorrect login or password");
    private final String message;

    AuthorizationCode(String message) {
        this.message = message;
    }
}

