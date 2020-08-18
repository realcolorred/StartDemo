package com.example.pubserv.util.redis.bo;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by realcolorred on 2019/3/12.
 */
@Getter
@ToString
public class RedisLockBo {

    private String  key;  //缓存Key
    private String  value;//缓存VALUE
    private boolean lock; //是否锁成功

    public RedisLockBo(String key, String value) {
        this(key, value, true);
    }

    public RedisLockBo(String key, String value, boolean lock) {
        this.key = key;
        this.value = value;
        this.lock = lock;
    }
}
