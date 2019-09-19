package com.example.demo.util.RSA;

import com.example.demo.util.RSA.RSABase;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/5/28.
 *
 */
public class RSAEncode extends RSABase {

    /**
     * 公钥加密
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // 获取公钥对象
        byte[] decoded = Base64.decodeBase64(publicKey);//base64解码后的Key
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        return encrypt(str, pubKey);
    }

    /**
     * 私钥解密
     * @param str
     * @param privateKey
     * @return
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        // 获取私钥对象
        byte[] decoded = Base64.decodeBase64(privateKey); //base64解码后的Key
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        return decrypt(str, priKey);
    }

}
