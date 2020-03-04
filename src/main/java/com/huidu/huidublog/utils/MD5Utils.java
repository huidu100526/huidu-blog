package com.huidu.huidublog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @auther huidu
 * @create 2020/2/22 15:54
 * @Description: md5加密工具类
 */
public class MD5Utils {
    private static final String SALT = "huidu";

    public static String md5(String str) {
        try {
            str = str + SALT; // 加盐
            MessageDigest md = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md.digest(byteArray);
            StringBuilder hexValue = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
