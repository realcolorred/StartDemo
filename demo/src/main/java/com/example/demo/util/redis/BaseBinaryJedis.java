package com.example.demo.util.redis;

import com.example.demo.util.PropertyUtil;
import com.example.demo.util.redis.command.IBinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;
import java.util.Random;

/**
 * Created by realcolorred on 2019/3/12.
 *
 * 二进制缓存基类
 * IdclogRedis存储涉及的redis二进制命令的基类
 */
public abstract class BaseBinaryJedis implements IBinaryJedis {

    private static final long   MILLI_NANO_TIME = 1000 * 1000L;   //纳秒和毫秒之间的转换率
    private static final String ENCODING        = "UTF-8";

    private static final Random RANDOM = new Random();

    @Override
    public RedisLock lock(String key, String value, long timeout, int expire) {
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
                    return new RedisLock(key, value, true);
                } else {
                    byte[] retBytes = get(keyBytes);
                    String retValue = new String(retBytes, ENCODING);

                    // 如果已经有了相同的锁,且锁的值相同,说明是同一个锁, 返回锁定成功 ,更新锁过期时间
                    if (value.equals(retValue)) {
                        expire(keyBytes, expire);
                        return new RedisLock(key, value, true);
                    }
                }

                //短暂休眠，避免可能的活锁
                Thread.sleep(3, RANDOM.nextInt(30));
            }
        } catch (Throwable t) {
            //todo logger.error("redis lock error!", t);
        }
        return new RedisLock(key, value, false);
    }

    @Override
    public boolean ulock(RedisLock redisLock) {
        try {
            if (redisLock.isLock()) {
                this.del(redisLock.getKey().getBytes(ENCODING));
            }
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 获取Jedis连接池配置
     *
     * @param props
     *            配置文件属性
     * @return Jedis连接池配置
     */
    protected JedisPoolConfig getConfig(Properties props) {
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMinEvictableIdleTimeMillis(PropertyUtil.getProperty(props, "min-evictable-idle-time-millis", 10000));//最小空闲时间
        jpc.setTimeBetweenEvictionRunsMillis(PropertyUtil.getProperty(props, "time-between-eviction-runs-millis", 10));//回收资源线程的执行周期
        jpc.setNumTestsPerEvictionRun(PropertyUtil.getProperty(props, "num-tests-per-eviction-run", -1));//回收资源的数量
        jpc.setLifo(PropertyUtil.getProperty(props, "lifo", true));//LIFO or FIFO
        jpc.setMaxTotal(PropertyUtil.getProperty(props, "max-total", 500));// 最大连接数
        jpc.setMinIdle(PropertyUtil.getProperty(props, "min-idle", 2));//最小空闲连接数
        jpc.setMaxIdle(PropertyUtil.getProperty(props, "max-idle", 20));//最大空闲连接
        jpc.setMaxWaitMillis(PropertyUtil.getProperty(props, "max-wait-millis", 2000));//获取连接超时等待
        jpc.setTestWhileIdle(PropertyUtil.getProperty(props, "test-while-idle", false));
        jpc.setTestOnBorrow(PropertyUtil.getProperty(props, "test-on-borrow", true));
        jpc.setTestOnReturn(PropertyUtil.getProperty(props, "test-on-return", false));
        jpc.setTestOnCreate(PropertyUtil.getProperty(props, "test-on-create", false));
        return jpc;
    }
}
