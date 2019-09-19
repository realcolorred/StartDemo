package com.example.demo.util.RSA;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/9/19.
 *
 * 1).RSA加密实现
 *
 * 公钥加密,私钥解密
 *
 * 非对称加密实现流程.
 * 1.己端A生成公钥A,私钥A.将公钥A发送给对端B
 * 2.对端B生成公钥B,私钥B,将公钥B发送给己端A
 * 3.己端A向对端B发送信息. 需要用公钥B加密信息, 对端B收到后用私钥B解密
 * 4.对端B向己端A发送信息. 需要用公钥A加密信息, 己端A收到后用私钥A解密
 *
 *
 *
 * 2).RSA签名实现
 *
 * 私钥加密,公钥解密
 *
 * 利用私钥加密信息,公布加密的信息以及公钥.如果公钥能够正确解密,则签名合法
 */
public class RSABase {

    public static final String PUBLIC_KEY  = "PUBLIC_KEY";    // 公钥名称
    public static final String PRIVATE_KEY = "PRIVATE_KEY";   // 私钥名称
    public static final String ENCODE      = "UTF-8";
    public static final String ALGORITHM   = "RSA"; // 算法

    public static int keyLength = 1024;

    /**
     * 获取随机生成的私钥和公钥
     * @return
     */
    public static Map<String, String> genKeyPairStr() throws NoSuchAlgorithmException {
        // 将密钥字符串化
        Map<String, Key> map = genKeyPairObj();
        String publicKeyString = new String(Base64.encodeBase64(map.get(PUBLIC_KEY).getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64((map.get(PRIVATE_KEY).getEncoded())));

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKeyString);
        keyMap.put(PRIVATE_KEY, privateKeyString);
        return keyMap;
    }

    protected static Map<String, Key> genKeyPairObj() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGen.initialize(keyLength, new SecureRandom());// 初始化密钥对生成器，密钥大小为 512-16384 位
        KeyPair keyPair = keyPairGen.generateKeyPair();// 生成一个密钥对，保存在keyPair中
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

        Map<String, Key> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 加密
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    protected static String encrypt(String str, Key key) throws Exception {
        //RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes(ENCODE)));
    }

    /**
     * 解密
     * @param str
     * @param key
     * @return
     */
    protected static String decrypt(String str, Key key) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes(ENCODE)); //64位解码后的加密字符串

        //RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(inputByte), ENCODE);
    }
}
