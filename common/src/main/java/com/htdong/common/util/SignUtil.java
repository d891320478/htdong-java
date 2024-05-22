package com.htdong.common.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class SignUtil {

    public static String md5Str(byte[] origin) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messagedigest.update(origin);
            return Hex.encodeHexString(messagedigest.digest(origin));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha256Str(byte[] origin) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            messagedigest.update(origin);
            return Hex.encodeHexString(messagedigest.digest(origin));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}