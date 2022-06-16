package com.it.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

public class PBE {



    public static final String ALGORUTHM = "PBEWITHMD5andDES";
    /**
     * 迭代次数
     */
    public static final int ITERATION_COUN = 100;

    /**
     * "盐"初始化<br>
     * 盐长度必须为8字节
     * @return byte[] 盐
     * @throws Exception
     */
    public static byte[] initSalt() throws Exception {
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        //产出盐
        return random.generateSeed(8);
    }
    /**
     * 转换密钥
     * @param password 密码
     * @return Key密钥
     * @throws Exception
     *
     */

    private static Key toKey(String password) throws Exception {
        //密钥材料转换
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORUTHM);
        //生成密钥
        SecretKey secretkey = keyFactory.generateSecret(keySpec);
        return secretkey;
    }

    /**
     * 加密
     * @param data 数据
     * @param password 密钥
     * @param salt 盐
     * @return byte[] 加密数据
     * @throws Exception
     */

    public static byte[] encrypt(byte[] data, String  password, byte[] salt) throws Exception{
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参考数据
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUN);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORUTHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param data 数据
     * @param password 密码
     * @param salt 盐
     * @return byte[] 解密数据
     * @throws Exception
     */

    public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception{
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUN);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORUTHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        //执行操作
        return cipher.doFinal(data);
    }

}
