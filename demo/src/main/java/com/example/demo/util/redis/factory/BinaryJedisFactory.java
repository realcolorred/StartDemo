package com.example.demo.util.redis.factory;

import com.example.demo.util.PropertyUtil;
import com.example.demo.util.redis.command.IBinaryJedis;
import com.example.demo.util.redis.inst.BinaryJedisCluster;
import com.example.demo.util.redis.inst.BinaryJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by realcolorred on 2019/3/12.
 * <p>
 * 二进制缓存工厂
 */
public class BinaryJedisFactory {

    protected static final Logger logger = LoggerFactory.getLogger(BinaryJedisFactory.class);

    private static final String CLUSTER = "cluster"; // 缓存类型 : 集群
    private static final String POOL    = "pool"; // 缓存类型 : 缓存池
    private static final String CTG     = "ctg"; // 缓存类型 : 集团缓存

    // 储存各种不同的缓存对象, 根据key值获取(不存在则初始化)不同的缓存对象
    private static final Map<String, IBinaryJedis> JEDIS_CACHE = new ConcurrentHashMap<String, IBinaryJedis>();

    public static IBinaryJedis getJedis(String redisKey) {
        IBinaryJedis binaryJedis = JEDIS_CACHE.get(redisKey);
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
        synchronized (JEDIS_CACHE) {
            jedis = JEDIS_CACHE.get(redisKey);
            if (jedis != null) {
                return jedis;
            }

            String jedisClass = props.getProperty("jedis-class"); // 缓存类型
            if (CLUSTER.equals(jedisClass)) {
                jedis = new BinaryJedisCluster(PropertyUtil.getProperties(props, "jedis-cluster.")); // 集群
            } else if (POOL.equals(jedisClass)) {
                jedis = new BinaryJedisPool(PropertyUtil.getProperties(props, "jedis-pool."));
            } else if (CTG.equals(jedisClass)) {
                //jedis = new BinaryJedisCtg(PropertyUtil.getProperties(props, "jedis-ctg."));
            } else {
                return null;
            }
            logger.info("创建了一个新的缓存对象:" + jedisClass);
            JEDIS_CACHE.put(redisKey, jedis);
        }
        return jedis;
    }

}
