package com.vise.activity;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class security {
    public static byte[] encrypt(String plainText, String key) throws Exception{
        byte[] get = plainText.getBytes();
        
        int IVSize = 16;
        byte[] iv = new byte[IVSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(key.getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypt = cipher.doFinal(get);
        
        byte[] encryptedIVAndText = new byte[IVSize + encrypt.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, IVSize);
        System.arraycopy(encrypt, 0, encryptedIVAndText, IVSize, encrypt.length);

        return encryptedIVAndText;
    }

    public static String decrypt(byte[] encryptedIvTextBytes, String key) throws Exception {
        int IVSize = 16;
        int KeySize = 16;

        byte[] iv = new byte[IVSize];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        int encryptedSize = encryptedIvTextBytes.length - IVSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, IVSize, encryptedBytes, 0, encryptedSize);
        
        byte[] keyBytes = new byte[KeySize];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(key.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        return new String(decrypted);
    }
}
