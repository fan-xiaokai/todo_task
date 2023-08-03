package com.todo.task.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author xtf
 * @Date 08/06/2020 16:16
 * @Description sha256加密  密码加密规则 先用MD5加密用户的密码 然后拼接随机生成的盐做sha加密
 * @Version 1.0
 */

public class Sha256Util {

    public static String getSha256(String password, String salt) {
        return new Sha256Hash(password, salt, 1024).toBase64();
    }

    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(HMACSHA256("F20210824储槽12" + 1,
                "keyc6e11c35dfea11eb9873000c297187da"));
    }

}
