package com.example.pubserv.util.redis;

import com.example.common.util.UUIDUtil;
import com.example.pubserv.constant.RedisConstant;
import com.example.pubserv.util.redis.bo.RedisLockBo;
import com.example.pubserv.util.redis.command.ILockCommand;
import com.example.pubserv.util.redis.factory.BinaryJedisFactory;

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
            lockCommand = BinaryJedisFactory.getJedis();
        }
        if (lockCommand == null) {
            throw new RuntimeException("加锁失败:不支持的加锁类型");
        }
        return lockCommand;
    }

    /**
     * 加锁
     * @param key 锁的KEY
     * @return 缓存锁
     */
    public static RedisLockBo lock(String key) {
        return getLockCommand().lock(key, UUIDUtil.getUUID(), RedisConstant.DEFAULT_REDIS_LOCK_TIME_OUT, RedisConstant.DEFAULT_REDIS_LOCK_EXPORE);
    }

    public static RedisLockBo lock(String key, String value, int expSecond) {
        return getLockCommand().lock(key, value, RedisConstant.DEFAULT_REDIS_LOCK_TIME_OUT, expSecond);
    }

    /**
     * 解锁
     * @param redisLock 缓存锁
     * @return 成功 or 失败
     */
    public static boolean ulock(RedisLockBo redisLock) {
        return getLockCommand().ulock(redisLock);
    }
}
