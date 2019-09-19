package com.example.demo.util.RSA;

import com.example.demo.util.RSA.RSABase;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * Created by lenovo on 2019/9/19.
 */
public class RSASign extends RSABase {
    /**
     * 私钥签名
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String str, String privateKey) throws Exception {
        // 获取私钥对象
        byte[] decoded = Base64.decodeBase64(privateKey); //base64解码后的Key
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        return encrypt(str, priKey);
    }

    /**
     * 公钥验证
     * @param str
     * @param publicKey
     * @return
     */
    public static String verify(String str, String publicKey) throws Exception {
        // 获取公钥对象
        byte[] decoded = Base64.decodeBase64(publicKey);//base64解码后的Key
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        return decrypt(str, pubKey);
    }
}
