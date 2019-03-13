package com.example.demo.util.redis;

/**
 * Created by realcolorred on 2019/3/12.
 */
public interface RedisConstans {
    public static final String REDIS_LOCK = "redis_lock";
    public static final String REDIS      = "redis";

    public static final long REDIS_LOCK_TIME_OUT = 1000;     // 超时时间 1000 ms
    public static final int  REDIS_LOCK_EXPORE   = 30;       // 缓存锁过期时间30s
}
