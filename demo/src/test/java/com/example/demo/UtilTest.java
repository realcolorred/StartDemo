package com.example.demo;

import com.example.demo.bean.KDM;
import com.example.demo.entity.KingEntity;
import com.example.demo.util.RSA.RSAEncode;
import com.example.demo.util.RSA.RSASign;
import com.example.demo.util.RedisLockUtil;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.UUIDUtil;
import com.example.demo.util.ValidatorUtil;
import com.example.demo.util.redis.RedisLock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.DigestUtils;

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
    public void ValidatorUtil(){
        KingEntity entity = new KingEntity();
        entity.setAge(11100);
        entity.setBirthday("-2019/11/1");
        System.out.println(ValidatorUtil.validate(entity));
    }

    @Test
    public void MoviesTest() throws Exception {
        /* 不安全因素
            1.影片泄露: 无解
            2.影片密码泄露: 导致该影片可被直接破解
                    解法: 由开发方单机生成存储,不允许查看
            3.KDM签名公钥泄露: 各个影院可以伪造签名,更改密文验证的哈希值.(密文仍无法破解,危害较低)
                    解法: 由开发方单机生成存储,不允许查看
            4.影院机器密钥泄露: 获取KDM后可以直接得到影片密码,无视生失效时间,约等于公开影片密码.
                    解法: 禁止影院拆解破解机器

            不安全部分: 影院机器密钥泄露
            可能的解决办法: 给每个机器提供的电影包都不一样,分发的密码因此也不一样.  缺点:压缩影片的负载巨大,传输的数据巨大.
         */
        // 开发方公开部分: 加密过的影片, KDM, KDM签名公钥
        // 开发方私密信息: 影片, 影片加密密码, KDM签名私钥

        // 影院机器公开部分: 生成的公钥
        // 影院机器私密部分: 生成的私钥

        Map<String, String> macKey = RSAEncode.genKeyPairStr();
        String publicKeyMac = macKey.get(RSAEncode.PUBLIC_KEY);
        String privateKeyMac = macKey.get(RSAEncode.PRIVATE_KEY);

        String key = "key";

        Map<String, String> devSignKey = RSASign.genKeyPairStr();
        String publicKeySignDev = devSignKey.get(RSASign.PUBLIC_KEY);
        String privateKeySignDev = devSignKey.get(RSASign.PRIVATE_KEY);

        String movies = "代指非常好看的一副影片";

        String moviesEn = en(movies, key);
        System.out.println("发行方加密了影片:" + moviesEn);
        System.out.println("影院机器获取了影片,但是缺少秘钥");

        KDM kdm = new KDM();
        kdm.setPubStr("这个是许可,生失效时间为:20190101-20200101");
        kdm.setPriStr(RSAEncode.encrypt("key," + key + ",effexpDate,20190101-20200101", publicKeyMac));
        kdm.setSign(RSASign
            .sign("开发方签名,公开部分和加密部分的md5值为:" + DigestUtils.md5DigestAsHex((kdm.getPubStr() + kdm.getPriStr()).getBytes()), privateKeySignDev));
        System.out.println("发行方根据影院机器的公钥制作了一份KMD:" + kdm.toString());
        System.out.println("发行方签名公钥为:" + publicKeySignDev);

        System.out.println("影院机器读取KMD:" + kdm.toString());
        String sign = RSASign.verify(kdm.getSign(), publicKeySignDev);
        System.out.println("影院机器验证签名正确:" + sign);
        System.out.println("影院机器验证密文哈希值:" + DigestUtils.md5DigestAsHex((kdm.getPubStr() + kdm.getPriStr()).getBytes()).equals(sign.split(":")[1]));
        String priStr = RSAEncode.decrypt(kdm.getPriStr(), privateKeyMac);
        String[] priStrList = priStr.split(",");
        System.out.println("影院机器解密成功:" + priStr);
        System.out.println("影院机器验证有效时间:" + priStrList[3]);
        System.out.println("影院机器播放电影:" + de(moviesEn, priStrList[1]));

    }

    private String en(String str, String key) {
        if (key.equals("key"))
            return "NSJXCBAISBSCSACSCASSDSDADASDASDASDASDASDC";
        else
            return "error";
    }

    private String de(String str, String key) {
        if (key.equals("key"))
            return "代指非常好看的一副影片";
        else
            return "error";
    }

    @Test
    public void RSASignTest() throws Exception {
        Map<String, String> mapKey = RSASign.genKeyPairStr();
        String publicKey = mapKey.get(RSASign.PUBLIC_KEY);
        String privateKey = mapKey.get(RSASign.PRIVATE_KEY);
        String str = "zengli签名再此";
        String strEn = RSASign.sign(str, privateKey);
        String strDe = RSASign.verify(strEn, publicKey);

        System.out.println("公钥为:" + publicKey);
        System.out.println("私钥为:" + privateKey);
        System.out.println("原信息为:" + str);
        System.out.println("加密后为:" + strEn);
        System.out.println("解密后为:" + strDe);
    }

    @Test
    public void RSATest() throws Exception {
        Map<String, String> mapKey = RSAEncode.genKeyPairStr();
        String publicKey = mapKey.get(RSAEncode.PUBLIC_KEY);
        String privateKey = mapKey.get(RSAEncode.PRIVATE_KEY);
        String str = "我的账号的zengli密码是123456789!!!";
        String strEn = RSAEncode.encrypt(str, publicKey);
        String strDe = RSAEncode.decrypt(strEn, privateKey);

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
