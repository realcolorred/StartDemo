package com.example.demo.util.redis.command;

import com.example.demo.util.redis.bo.RedisLockBo;

/**
 * Created by realcolorred on 2019/3/12.
 *
 * redis缓存锁
 */
public interface ILockCommand {
    /**
     * 加锁
     * @param key 锁的KEY
     * @param value 锁的VALUE
     * @param timeout 获取锁的超时时间(毫秒)
     * @param expire 分布式锁中分组的key的过期时间，过期后锁将失效(秒)
     * @return 缓存锁
     */
    public RedisLockBo lock(String key, String value, long timeout, int expire);

    /**
     * 解锁
     * @param redisLock 缓存锁
     * @return 成功 or 失败
     */
    public boolean ulock(RedisLockBo redisLock);
}
