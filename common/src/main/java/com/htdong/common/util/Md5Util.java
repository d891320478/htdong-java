package com.htdong.common.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class Md5Util {

    public static String md5Str(String str) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            messagedigest.update(str.getBytes("UTF-8"));
            return Hex.encodeHexString(messagedigest.digest(str.getBytes()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(md5Str("GaxwZCT"));
    }
}