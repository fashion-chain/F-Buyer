package com.hottop.core.utils;

import org.apache.commons.codec.Charsets;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class RsaUtil {
    private static final String ALGORITHM = "RSA";
    private static final Integer DEFAULT_SIZE = 1024;

    private HashMap<String, KeyPair> rsaMapper;

    public RsaUtil() {
        this.rsaMapper = new HashMap<>();
    }

    public boolean initRsaPair(String key) {
        if (this.rsaMapper.containsKey(key)) {
            return false;
        }
        this.rsaMapper.put(key, generateKeyPair());
        return true;
    }

    public KeyPair getRsaPair(String key) throws Exception {
        if (!this.rsaMapper.containsKey(key)) {
            throw new Exception("specific keypair not exists");
        }
        return this.rsaMapper.get(key);
    }

    public static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(DEFAULT_SIZE);
        } catch (NoSuchAlgorithmException ex) {
            LogUtil.error(ex.getStackTrace());
        }
        return keyPairGenerator.generateKeyPair();
    }

    public static PublicKey getPublicKeyByBase64edKey(String base64edKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64edKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKeyByBase64edKey(String base64edKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64edKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    public static String encryptByKey(String unEncryptedMessage, Key encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        byte[] bytes = cipher.doFinal(unEncryptedMessage.getBytes(Charsets.UTF_8));
        return Base64.getMimeEncoder().encodeToString(bytes);
    }

    public static String decryptByKey(String encryptedMessage, Key decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, decryptKey);
        byte[] bytes = cipher.doFinal(Base64.getMimeDecoder().decode(encryptedMessage.getBytes(Charsets.UTF_8)));
        return new String(bytes, Charsets.UTF_8);
    }
}
