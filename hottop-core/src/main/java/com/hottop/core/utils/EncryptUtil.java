package com.hottop.core.utils;

import org.springframework.util.Base64Utils;

import java.security.SecureRandom;

public class EncryptUtil {
    public static String generateSalt(int saltLength) {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[saltLength];
        sr.nextBytes(salt);
        return Base64Utils.encodeToString(salt).substring(0, saltLength);
    }

    public static String generatePassword(String explicitPwd, String salt) {
        return null;
    }

    public static boolean isPasswordCorrect(String password, String salt) {
        return false;
    }
}
