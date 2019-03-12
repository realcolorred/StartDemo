package com.example.demo.util.redis;

/**
 * Created by realcolorred on 2019/3/12.
 */
public class RedisLock {

    private final String  key;  //缓存Key
    private final String  value;//缓存VALUE
    private final boolean lock; //是否锁成功

    public RedisLock(String key, String value) {
        this(key, value, true);
    }

    public RedisLock(String key, String value, boolean lock) {
        this.key = key;
        this.value = value;
        this.lock = lock;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isLock() {
        return lock;
    }
}
