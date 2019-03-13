package com.example.demo.util.redis;

import com.example.demo.util.PropertyUtil;
import com.example.demo.util.redis.command.IBinaryJedis;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by realcolorred on 2019/3/12.
 *
 *  二进制缓存工厂
 */
public class BinaryJedisFactory {

    // 储存各种不同的缓存对象, 根据key值获取(不存在则初始化)不同的缓存对象
    private static final Map<String, IBinaryJedis> jedisCache = new ConcurrentHashMap<String, IBinaryJedis>();

    public static IBinaryJedis getJedis(String redisKey) {
        IBinaryJedis binaryJedis = jedisCache.get(redisKey);
        if (binaryJedis != null) {
            return binaryJedis;
        }
        Properties props = PropertyUtil.getProperties(redisKey + ".properties");
        if (props == null || props.isEmpty()) {
            return null;
        }
        return initJedis(redisKey, props);
    }

    private static IBinaryJedis initJedis(String redisKey, Properties props) {
        IBinaryJedis jedis;
        synchronized (jedisCache) {
            jedis = jedisCache.get(redisKey);
            if (jedis != null) {
                return jedis;
            }

            String jedisClass = props.getProperty("jedis-class"); // 缓存类型
            if ("cluster".equals(jedisClass)) {
                jedis = new BinaryJedisCluster(PropertyUtil.getProperties(props, "jedis-cluster.")); // 集群
            } else if ("pool".equals(jedisClass)) {
                jedis = new BinaryJedisPool(PropertyUtil.getProperties(props, "jedis-pool."));
            } else {
                return null;
            }
            jedisCache.put(redisKey, jedis);
        }
        return jedis;
    }

}
