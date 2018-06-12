package com.praveen.commons.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Encrypts and decrypts strings (e.g. passwords to store in property files)
 */
public class Encrypter {

    private static final char[] PASSWORD = "2102knabzremmoCtcejorPpilC".toCharArray();
    private static final byte[] SALT = { //
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, /**/
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, };

    /**
     * Main method to encrypt an existing password to store in a property file
     * 
     * @param args enc[rypt] password
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            String command = args[0];
            String passwd = args[1];
            if ("enc".equals(command.toLowerCase().substring(0, 3))) {
                System.out.println("Encrypted password: " + encrypt(passwd));
                return;
                // } else if ("dec".equals(command.toLowerCase().substring(0,
                // 3))) {
                // System.out.println("Decrypted password: " + decrypt(passwd));
                // return;
            }
        }
        System.out.println("usage: enc[rypt] password");
    }

    /**
     * Encrypt an property (as string)
     * 
     * @param property property to encrypt
     * @return encrypted string
     * @throws GeneralSecurityException
     */
    public static String encrypt(String property) throws GeneralSecurityException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes()));
    }

    /**
     * Decrypt an property (as string)
     * 
     * @param property to decrypt
     * @return decrypted string
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)));
    }

    /**
     * Encode base64
     * 
     * @param bytes
     * @return base64 string
     */
    private static String base64Encode(byte[] bytes) {
        byte[] base64 = Base64.encodeBase64(bytes);
        String result = "";
        for (int i = 0; i < base64.length; i++) {
            result += (char) base64[i];
        }
        return result;
    }

    /**
     * Decode base64
     * 
     * @param property base64 string
     * @return decoded byte array
     * @throws IOException
     */
    private static byte[] base64Decode(String property) throws IOException {
        return Base64.decodeBase64(property.getBytes());
    }

}
