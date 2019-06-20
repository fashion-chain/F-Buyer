package com.hottop.core.utils;

<<<<<<< HEAD
import org.springframework.util.Base64Utils;

import java.security.SecureRandom;

public class EncryptUtil {
=======
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class EncryptUtil {
    public static String randomAlphaNumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    public static String generateSalt(int saltLength) {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[saltLength];
        sr.nextBytes(salt);
        return Base64Utils.encodeToString(salt).substring(0, saltLength);
<<<<<<< HEAD
    }

    public static String generatePassword(String explicitPwd, String salt) {
        return null;
    }

    public static boolean isPasswordCorrect(String password, String salt) {
        return false;
    }
}
=======
//        return randomAlphaNumeric(saltLength);
    }

    /**
     * 生成密码
     * @param explicitPwd
     * @param salt
     * @return
     */
    public static String generatePassword(String explicitPwd, String salt) {
        return MD5Encode(explicitPwd+salt, "utf-8");
    }

    /**
     * 字符串 md5加密
     * @param origin 原字符串
     * @param charset 字符编码
     * @return 小写字符
     */
    private static String MD5Encode(String origin, String charset) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charset == null || "".equals(charset))
                resultString = bytesToHex(md.digest(resultString.getBytes()));
            else
                resultString = bytesToHex(md.digest(resultString.getBytes(charset)));
        } catch (Exception exception) {
        }
        return resultString.toLowerCase();
    }

    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static boolean isPasswordCorrect(String password, String salt) {
        return false;
    }
}
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
