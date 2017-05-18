package org.wx.weixiao.util;

import com.google.gson.Gson;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2017/1/4.
 * 加密解密类，对指定信息进行加密和解密，可以接受map类型参数
 */
public class SecurityUtil {
    /**
     * 注意key和加密用到的字符串是不一样的 加密还要指定填充的加密模式和填充模式 AES密钥可以是128或者256，加密模式包括ECB, CBC等
     * ECB模式是分组的模式，CBC是分块加密后，每块与前一块的加密结果异或后再加密 第一块加密的明文是与IV变量进行异或
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密信息
     *
     * @param mess  需要加密的信息
     * @param token 加密使用的token
     * @return
     */
    public static String encode(String mess, String token) {
        SecretKey secretKey = generateAESSecretKey(token);
        try {
            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(mess.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密信息
     *
     * @param mess  需要解密的信息
     * @param token 解密使用的token
     * @return
     */
    public static String decode(String mess, String token) {
        SecretKey secretKey = generateAESSecretKey(token);
        try {
            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(mess)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将需要传递的参数进行加密，返回加密后的字符串
     *
     * @param parameters 需要加密的参数键值对
     * @param token      加密需要的token
     * @return
     */
    public static <T> String encode(Map<String, T> parameters, String token) {
        return encode(new Gson().toJson(parameters), token);
    }

    /**
     * 根据解密信息，返回map类型的参数
     *
     * @param encodeMess
     * @param token
     * @return
     */
    public static <T> Map<String, T> decodeAsMap(String encodeMess, String token) {
        String json = decode(encodeMess, token);
        if (json == null || !CommonUtil.isJson(json))
            return null;
        try {
            return new Gson().fromJson(json, Map.class);
        } catch (Exception e) {
            System.err.println("format error");
        }
        return null;
    }


    /**
     * 根据指定的token获得加密解密的秘钥
     *
     * @return 指定的秘钥
     */

    private static SecretKey generateAESSecretKey(String token) {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            // keyGenerator.init(256);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(token.getBytes());
            keyGenerator.init(secureRandom);
            return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        Map<String, String> paras = new HashMap<>();
        paras.put("question_id","92");
        paras.put("answerer_id","13");
        paras.put("flag","0");
        //paras.put("flag", "0");
        String token = "mytoken";

        String originMess = new Gson().toJson(paras);
        System.out.println("origin mess:" + originMess);
        String encodeMess = encode(paras, token);
        System.out.println("encode mess:" + encodeMess);
        String decodeMess = new Gson().toJson(decodeAsMap("3bsQ1+RLadgbE+8Ho9wRqrP4FKKibJX0vv6QM4b5aTTwRslnBgD665s7vkKUXNxGODBQz52wH5sXJnrf73bHqA==", token));
        System.out.println("decode mess:" + decodeMess);
    }
}
