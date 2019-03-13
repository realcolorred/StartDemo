package com.example.demo.util;

import com.example.demo.util.redis.BinaryJedisFactory;
import com.example.demo.util.redis.RedisConstans;
import com.example.demo.util.redis.RedisLock;
import com.example.demo.util.redis.command.ILockCommand;

/**
 * 缓存锁工具
 *
 */
public class RedisLockUtil {

    private static ILockCommand lockCommand;

    private static ILockCommand getLockCommand() {
        if (lockCommand != null) {
            return lockCommand;
        }
        synchronized (ILockCommand.class) {
            if (lockCommand != null) {
                return lockCommand;
            }
            // 初始化
            lockCommand = BinaryJedisFactory.getJedis(RedisConstans.REDIS_LOCK);
        }
        if (lockCommand == null) {
            throw new RuntimeException("加锁失败:不支持的加锁类型" + RedisConstans.REDIS_LOCK);
        }
        return lockCommand;
    }

    /**
     * 加锁
     * @param key 锁的KEY
     * @param timeout 获取锁的超时时间(毫秒)
     * @param expire 分布式锁中分组的key的过期时间，过期后锁将失效(秒)
     * @return 缓存锁
     */
    public static RedisLock lock(String key, long timeout, int expire) {
        return getLockCommand().lock(key, UUIDUtil.getUUID(), timeout, expire);
    }

    /**
     * 解锁
     * @param redisLock 缓存锁
     * @return 成功 or 失败
     */
    public static boolean ulock(RedisLock redisLock) {
        return getLockCommand().ulock(redisLock);
    }
}
