package com.example.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/5/28.
 *
 * RSA加密实现
 *
 * 非对称加密实现流程.
 * 1.己端A生成公钥A,私钥A.将公钥A发送给对端B
 * 2.对端B生成公钥B,私钥B,将公钥B发送给己端A
 * 3.己端A向对端B发送信息. 需要用公钥B加密信息, 对端B收到后用私钥B解密
 * 4.对端B向己端A发送信息. 需要用公钥A加密信息, 己端A收到后用私钥A解密
 *
 *
 */
public class RSAUtil {

    private static String PUBLIC_KEY_NAME  = "PUBLIC_KEY";    // 公钥名称
    private static String PRIVATE_KEY_NAME = "PRIVATE_KEY";   // 私钥名称
    private static String ALGORITHM        = "RSA"; // 算法
    private static String ENCODE           = "UTF-8";

    /**
     * 获取随机生成的私钥和公钥
     * @return
     */
    public static Map<String, String> genKeyPair() {
        Map<String, String> keyMap = new HashMap<>();

        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // todo 打印日志
            return null;
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(PUBLIC_KEY_NAME, publicKeyString);  //0表示公钥
        keyMap.put(PRIVATE_KEY_NAME, privateKeyString);  //1表示私钥
        return keyMap;
    }

    /**
     * 利用公钥对信息进行加密
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey) {
        //base64编码的公钥
        String outStr;
        byte[] decoded = Base64.decodeBase64(publicKey);
        try {
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(ENCODE)));
        } catch (Exception e) {
            // todo 打印日志
            return null;
        }
        return outStr;
    }

    /**
     * 利用私钥对信息进行解密
     * @param str
     * @param privateKey
     * @return
     */
    public static String decrypt(String str, String privateKey) {
        //64位解码加密后的字符串
        String outStr;
        try {
            byte[] inputByte = Base64.decodeBase64(str.getBytes(ENCODE));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte), ENCODE);
        } catch (Exception e) {
            // todo 打印日志
            return null;
        }
        return outStr;
    }

    public static String getPublicKey(Map<String, String> map) {
        return map.get(PUBLIC_KEY_NAME);
    }

    public static String getPrivateKey(Map<String, String> map) {
        return map.get(PRIVATE_KEY_NAME);
    }

}
