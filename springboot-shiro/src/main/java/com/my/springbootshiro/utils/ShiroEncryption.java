package com.my.springbootshiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * shiro加密算法
 * @author xsh
 * @date 2020/6/28
 * @return
 */
public class ShiroEncryption {

    /**
     * 对用户密码进行加密:MD5
     * @param password
     * @return
     */
    public static String shiroMd5Encryption(String password) {
        // 算法名称
        String algorithmName = "md5";
        String encodedPassword = new SimpleHash(algorithmName,password).toString();
        // 返回加密后的密码
        return encodedPassword;
    }

    /**
     * 对用户密码进行加密
     * @param password
     * @param salt
     * @return
     */
    public static String shiroEncryption(String password,String salt) {
        // 加密次数
        int times = 1;
        // 算法名称
        String algorithmName = "md5";
        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();
        // 返回加密后的密码
        return encodedPassword;
    }

    public static void main(String[] args) {
        String password = "123456";
        String encodedPassword = shiroMd5Encryption(password);
        System.out.println(encodedPassword);
    }
}
