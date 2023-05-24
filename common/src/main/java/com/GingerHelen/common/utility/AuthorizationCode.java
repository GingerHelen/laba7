package com.GingerHelen.common.utility;

import lombok.Getter;

/**
 * перечисляемый тип данных с состояниями авторизации
 */
@Getter
public enum AuthorizationCode {
    AUTHORIZATION,
    REGISTRATION,
    ERROR
}

