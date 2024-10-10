package com.iris.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @create 2021-09
 * @description:
 */
@Slf4j
public class EncryptUtil {

    /**
     * AES解密
     * @param encryptStr 密文
     * @param decryptKey 秘钥，必须为16个字符组成
     * @return 明文
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey){
        try {
            if (StringUtils.isEmpty(encryptStr) || StringUtils.isEmpty(decryptKey)) {
                return null;
            }

            byte[] encryptByte = Base64.getDecoder().decode(encryptStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptByte);
            return new String(decryptBytes);
        } catch (Exception e) {
            log.error("AES解密失败", e);
        }
        return null;
    }

    /**
     * AES加密
     * @param content 明文
     * @param encryptKey 秘钥，必须为16个字符组成
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey){
        try {
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
                return null;
            }

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

            byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptStr);
        } catch (Exception e) {
            log.error("AES加密失败", e);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            String name = encrypt("2203", "4a8edf44de99531c");
            String pwd = encrypt("123", "4a8edf44de99531c");
            String client = encrypt("miniApp", "4a8edf44de99531c");
            log.info("用户名:{},密码:{},client:{}",name,pwd,client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
