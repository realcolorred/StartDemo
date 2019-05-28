package com.example.demo;

import com.example.demo.util.RSAUtil;
import com.example.demo.util.RedisLockUtil;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.UUIDUtil;
import com.example.demo.util.redis.RedisLock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class UtilTest {

    @BeforeClass
    public static void beforeTest() {
        System.out.println("============测试开始================");
    }

    @AfterClass
    public static void afterTest() {
        System.out.println("============测试结束================");
    }

    @Test
    public void RSATest() {
        Map<String, String> mapKey = RSAUtil.genKeyPair();
        String publicKey = RSAUtil.getPublicKey(mapKey);
        String privateKey = RSAUtil.getPrivateKey(mapKey);
        String str = "我的账号的zengli密码是123456789!!!";
        String strEn = RSAUtil.encrypt(str, publicKey);
        String strDe = RSAUtil.decrypt(strEn, privateKey);

        System.out.println("公钥为:" + publicKey);
        System.out.println("私钥为:" + privateKey);
        System.out.println("原信息为:" + str);
        System.out.println("加密后为:" + strEn);
        System.out.println("解密后为:" + strDe);
    }

    @Test
    public void uidTest() {
        System.out.println(UUIDUtil.getUUID());
    }

    @Test
    public void redisTest() {
        System.out.println(RedisUtil.existKey("test_key"));
        RedisLock lock = RedisLockUtil.lock("lock1");
        System.out.println("锁定结果" + lock);
        System.out.println("解锁结果:" + RedisLockUtil.ulock(lock));
    }

}
