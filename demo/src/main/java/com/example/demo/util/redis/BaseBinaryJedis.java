package com.example.demo.util.redis;

import com.example.demo.util.NumberHelper;
import com.example.demo.util.redis.command.IBinaryJedis;
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
    private static final Random RANDOM          = new Random();

    /**
     * 锁定
     * @param key 锁的KEY
     * @param value 锁的VALUE
     * @param timeout 获取锁的超时时间(毫秒)
     * @param expire 分布式锁中分组的key的过期时间，过期后锁将失效(毫秒)
     * @return
     */
    @Override
    public RedisLock lock(String key, String value, long timeout, int expire) {
        long startNanoTime = System.nanoTime();
        timeout *= MILLI_NANO_TIME;

        try {
            byte[] keyBytes = key.getBytes("UTF-8");
            byte[] valueBytes = value.getBytes("UTF-8");

            //在timeout的时间范围内不断轮询锁
            while (System.nanoTime() - startNanoTime < timeout) {

                Long ret = setnx(keyBytes, valueBytes);//锁不存在的话，设置锁并设置锁过期时间，即加锁
                if (ret != null && ret == 1) {
                    expire(keyBytes, expire);// 加锁成功 , 为锁设置一个过期时间 (设置锁过期时间是为了在没有释放锁的情况下锁过期后消失，不会造成永久阻塞)
                    return new RedisLock(key, value, true);
                } else {
                    byte[] retBytes = get(keyBytes);
                    String retValue = new String(retBytes, "UTF-8");

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

    /**
     * 解锁
     * @param redisLock 缓存锁
     * @return
     */
    @Override
    public boolean ulock(RedisLock redisLock) {
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
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(string)
     */
    protected String getProperty(Properties props, String key, String defaultValue) {
        String value = props.getProperty(key, defaultValue);
        return value != null ? value.trim() : null;
    }

    /**
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(int)
     */
    protected int getProperty(Properties props, String key, int defaultValue) {
        return NumberHelper.toInt(props.getProperty(key), defaultValue);
    }

    /**
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(boolean)
     */
    protected boolean getProperty(Properties props, String key, boolean defaultValue) {
        return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
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

        //最小空闲时间
        jpc.setMinEvictableIdleTimeMillis(getProperty(props, "min-evictable-idle-time-millis", 10000));

        //回收资源线程的执行周期
        jpc.setTimeBetweenEvictionRunsMillis(getProperty(props, "time-between-eviction-runs-millis", 10));

        //回收资源的数量
        jpc.setNumTestsPerEvictionRun(getProperty(props, "num-tests-per-eviction-run", -1));

        //LIFO or FIFO
        jpc.setLifo(getProperty(props, "lifo", true));

        // 最大连接数
        jpc.setMaxTotal(getProperty(props, "max-total", 500));

        //最小空闲连接数
        jpc.setMinIdle(getProperty(props, "min-idle", 2));

        //最大空闲连接
        jpc.setMaxIdle(getProperty(props, "max-idle", 20));

        //获取连接超时等待
        jpc.setMaxWaitMillis(getProperty(props, "max-wait-millis", 2000));

        jpc.setTestWhileIdle(getProperty(props, "test-while-idle", false));
        jpc.setTestOnBorrow(getProperty(props, "test-on-borrow", true));
        jpc.setTestOnReturn(getProperty(props, "test-on-return", false));
        jpc.setTestOnCreate(getProperty(props, "test-on-create", false));
        return jpc;
    }
}
