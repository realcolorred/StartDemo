package com.example.pubserv.constant;

/**
 * Created by realcolorred on 2019/3/12.
 */
public class RedisConstant {

    public static final long DEFAULT_REDIS_LOCK_TIME_OUT = 1000;     // 超时时间 1000 ms
    public static final int  DEFAULT_REDIS_LOCK_EXPORE   = 30;       // 缓存锁过期时间30s

    public static final String CLUSTER = "cluster"; // 缓存类型 : 集群
    public static final String POOL    = "pool"; // 缓存类型 : 缓存池
    public static final String CTG     = "ctg"; // 缓存类型 : 集团缓存

}
