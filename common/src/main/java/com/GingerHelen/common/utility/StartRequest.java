package com.GingerHelen.common.utility;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class StartRequest implements Serializable {
    private final User user;

    public StartRequest(User user) {
        this.user = user;
    }
}
