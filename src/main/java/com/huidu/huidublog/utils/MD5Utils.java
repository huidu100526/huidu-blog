package com.huidu.huidublog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @auther huidu
 * @create 2020/2/22 15:54
 * @Description: md5加密工具类
 */
public class MD5Utils {
    public static String md5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aByteDigest : byteDigest) {
                i = aByteDigest;
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
