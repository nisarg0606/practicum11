package com.prac.practicum11.security;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
//import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class Jwks {
    private Jwks() {
    }

    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString()).build();
    }
}
