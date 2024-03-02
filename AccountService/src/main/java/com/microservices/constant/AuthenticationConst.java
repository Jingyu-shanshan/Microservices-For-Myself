package com.microservices.constant;

public class AuthenticationConst {
    public static final String EncryptAlgorithm = "PBKDF2WithHmacSHA256";
    public static final String SecretKeyAlgorithm = "AES";
    public static final String CipherTransformation = "AES/CBC/PKCS5Padding";
    public static final String CipherCharsetName = "UTF-8";
    public static final String SecretKey = "microservice_key";
    public static final String Salt = "salt_1";
}
