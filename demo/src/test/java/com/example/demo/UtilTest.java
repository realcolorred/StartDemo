package com.example.demo;

import com.example.demo.util.RedisLockUtil;
import com.example.demo.util.redis.RedisLock;

public class UtilTest {

    public static void main(String args[]) {
        RedisLock lock = RedisLockUtil.lock("lock-2");
        System.out.println(lock);
    }
}
