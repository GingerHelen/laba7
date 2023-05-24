package com.GingerHelen.common.utility;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

/**
 * класс, реализующий первый ответ от сервера клиенту, через который он получает сведения о доступных ему командах
 */
@Getter
public class StartResponse implements Serializable {
    private HashMap<String, Requirement> commands;
    private final AuthorizationCode authorizationCode;
    private final String message;

    public StartResponse(HashMap<String, Requirement> commands, AuthorizationCode authorizationCode, String message) {
        this.commands = commands;
        this.authorizationCode = authorizationCode;
        this.message = message;
    }

    public StartResponse(AuthorizationCode authorizationCode, String message) {
        this.message = message;
        this.authorizationCode = authorizationCode;
    }
}
