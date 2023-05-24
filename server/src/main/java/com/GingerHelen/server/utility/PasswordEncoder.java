package com.GingerHelen.server.utility;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * класс, хэширующий пароль
 */
public final class PasswordEncoder {

    private PasswordEncoder(){
    }
    private final static Hasher hasher = Hashing.sha256().newHasher();

    public static String hash(String password) {

        hasher.putString(password, Charsets.UTF_8);
        return hasher.hash().toString();
    }

}
