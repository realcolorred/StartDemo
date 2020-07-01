package com.example.demo.util.redis.bo;

/**
 * Created by realcolorred on 2019/3/12.
 */
public class RedisLockBo {

    private final String  key;  //缓存Key
    private final String  value;//缓存VALUE
    private final boolean lock; //是否锁成功

    public RedisLockBo(String key, String value) {
        this(key, value, true);
    }

    public RedisLockBo(String key, String value, boolean lock) {
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

    @Override
    public String toString() {
        return "RedisLockBo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", lock=" + lock +
                '}';
    }
}
