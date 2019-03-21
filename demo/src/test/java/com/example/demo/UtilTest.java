package com.example.demo;

import com.example.demo.util.RedisLockUtil;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.redis.RedisLock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilTest {

    @BeforeClass
    public static void beforeTest(){
        System.out.println("============测试开始================");
    }

    @Test
    public void redisTest() {
        System.out.println(RedisUtil.existKey("test_key"));
        RedisLock lock =RedisLockUtil.lock("lock1");
        System.out.println("锁定结果"+lock);
        System.out.println("解锁结果:"+RedisLockUtil.ulock(lock));
    }

    @AfterClass
    public static void afterTest(){
        System.out.println("============测试结束================");
    }

}
