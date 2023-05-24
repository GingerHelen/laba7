package com.GingerHelen.common.utility;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {
    private final String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
