package com.microservices.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {
    @Test
    void givenPassword_whenEncrypt_thenReturnEncryptedPassword() throws Exception {
        String rawPassword = "password";

        String result = Authentication.encrypt(rawPassword, "key", "1");

        Assertions.assertNotNull(result);
    }

    @Test
    void givenEncryptedPassword_whenDecrypt_thenPasswordIsMatched() throws Exception {
        String rawPassword = "password";
        String key = "key";
        String salt = "1";

        String encryptedPassword = Authentication.encrypt(rawPassword, key, salt);

        String result = Authentication.decrypt(encryptedPassword, key, salt);

        Assertions.assertEquals(rawPassword, result);
    }
}
