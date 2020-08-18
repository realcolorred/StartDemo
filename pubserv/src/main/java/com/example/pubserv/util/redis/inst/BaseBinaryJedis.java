package com.example.pubserv.util.redis.inst;

import com.example.common.util.StringUtil;
import com.example.pubserv.config.RedisPoolConfig;
import com.example.pubserv.util.redis.bo.RedisLockBo;
import com.example.pubserv.util.redis.command.IBinaryJedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;

/**
 * Created by realcolorred on 2019/3/12.
 * <p>
 * 二进制缓存基类
 * IdclogRedis存储涉及的redis二进制命令的基类
 */
public abstract class BaseBinaryJedis implements IBinaryJedis {

    protected static final Logger logger = LoggerFactory.getLogger(BaseBinaryJedis.class);

    private static final long   MILLI_NANO_TIME = 1000 * 1000L;   //纳秒和毫秒之间的转换率
    private static final String ENCODING        = "UTF-8";

    private static final Random RANDOM = new Random();

    @Override
    public RedisLockBo lock(String key, String value, long timeout, int expire) {
        long startNanoTime = System.nanoTime();
        timeout *= MILLI_NANO_TIME;

        try {
            byte[] keyBytes = key.getBytes(ENCODING);
            byte[] valueBytes = value.getBytes(ENCODING);

            //在timeout的时间范围内不断轮询锁
            while (System.nanoTime() - startNanoTime < timeout) {

                Long ret = setnx(keyBytes, valueBytes);//锁不存在的话，设置锁并设置锁过期时间，即加锁
                if (ret != null && ret == 1) {
                    expire(keyBytes, expire);// 加锁成功 , 为锁设置一个过期时间 (设置锁过期时间是为了在没有释放锁的情况下锁过期后消失，不会造成永久阻塞)
                    return new RedisLockBo(key, value, true);
                } else {
                    byte[] retBytes = get(keyBytes);
                    String retValue = new String(retBytes, ENCODING);

                    // 如果已经有了相同的锁,且锁的值相同,说明是同一个锁, 返回锁定成功 ,更新锁过期时间
                    if (value.equals(retValue)) {
                        expire(keyBytes, expire);
                        return new RedisLockBo(key, value, true);
                    }
                }

                //短暂休眠，避免可能的活锁
                Thread.sleep(3, RANDOM.nextInt(30));
            }
        } catch (Throwable t) {
            logger.error("redis lock error!", t);
        }
        return new RedisLockBo(key, value, false);
    }

    @Override
    public boolean ulock(RedisLockBo redisLock) {
        try {
            if (redisLock.isLock()) {
                this.del(redisLock.getKey().getBytes("UTF-8"));
            }
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 获取Jedis连接池配置
     * @param redisPoolConfig
     * @return
     */
    protected JedisPoolConfig getConfig(RedisPoolConfig redisPoolConfig) {
        JedisPoolConfig jpc = new JedisPoolConfig();
        /*
        jpc.setMinEvictableIdleTimeMillis(getValue(props, "min-evictable-idle-time-millis", 10000));//最小空闲时间
        jpc.setTimeBetweenEvictionRunsMillis(getValue(props, "time-between-eviction-runs-millis", 10));//回收资源线程的执行周期
        jpc.setNumTestsPerEvictionRun(getValue(props, "num-tests-per-eviction-run", -1));//回收资源的数量
        jpc.setLifo(getValue(props, "lifo", true));//LIFO or FIFO
        jpc.setMaxTotal(getValue(props, "max-total", 500));// 最大连接数
        jpc.setMinIdle(getValue(props, "min-idle", 2));//最小空闲连接数
        jpc.setMaxIdle(getValue(props, "max-idle", 20));//最大空闲连接
        jpc.setMaxWaitMillis(getValue(props, "max-wait-millis", 2000));//获取连接超时等待
        jpc.setTestWhileIdle(getValue(props, "test-while-idle", false));
        jpc.setTestOnBorrow(getValue(props, "test-on-borrow", true));
        jpc.setTestOnReturn(getValue(props, "test-on-return", false));
        jpc.setTestOnCreate(getValue(props, "test-on-create", false));
        */
        jpc.setMinEvictableIdleTimeMillis(10000);//最小空闲时间
        jpc.setTimeBetweenEvictionRunsMillis(10);//回收资源线程的执行周期
        jpc.setNumTestsPerEvictionRun(-1);//回收资源的数量
        jpc.setLifo(true);//LIFO or FIFO
        jpc.setMaxTotal(500);// 最大连接数
        jpc.setMinIdle(2);//最小空闲连接数
        jpc.setMaxIdle(20);//最大空闲连接
        jpc.setMaxWaitMillis(2000);//获取连接超时等待
        jpc.setTestWhileIdle(false);
        jpc.setTestOnBorrow(true);
        jpc.setTestOnReturn(false);
        jpc.setTestOnCreate(false);
        return jpc;
    }

    protected String getValue(String value, String defaultValue) {
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    protected int getValue(String value, int defaultValue) {
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
}
