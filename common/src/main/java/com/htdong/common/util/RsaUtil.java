package com.htdong.common.util;

import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

/**
 * @author dht31261
 * @date 2024年5月15日 19:27:38
 * 
 * pem证书文件
 */
public class RsaUtil {

    public static String encrypt(String origin, String publicKeyFile) {
        try {
            File file = new File(publicKeyFile);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey;
            try (FileReader keyReader = new FileReader(file); PemReader pemReader = new PemReader(keyReader)) {
                PemObject pemObject = pemReader.readPemObject();
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pemObject.getContent());
                publicKey = factory.generatePublic(pubKeySpec);
            }
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(origin.getBytes());
            return Hex.encodeHexString(encryptedBytes);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(byte[] encrypt, String privateKeyFile) {
        try {
            File file = new File(privateKeyFile);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey;
            try (FileReader keyReader = new FileReader(file); PemReader pemReader = new PemReader(keyReader)) {
                PemObject pemObject = pemReader.readPemObject();
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
                privateKey = keyFactory.generatePrivate(keySpec);
            }
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encrypt);
            return new String(decryptedBytes);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}