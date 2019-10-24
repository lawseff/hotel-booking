package com.epam.booking.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    private static final String ALGORITHM = "MD5";

    public static String encryptPassword(String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        byte[] bytes = password.getBytes();
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest();

        return DatatypeConverter.printHexBinary(digest)
                .toLowerCase();
    }

}