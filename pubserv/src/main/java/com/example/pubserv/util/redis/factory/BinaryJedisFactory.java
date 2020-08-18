package com.example.pubserv.util.redis.factory;

import com.example.pubserv.config.RedisPoolConfig;
import com.example.pubserv.util.SpringBeanUtils;
import com.example.pubserv.util.redis.command.IBinaryJedis;
import com.example.pubserv.util.redis.inst.BinaryJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by realcolorred on 2019/3/12.
 * <p>
 * 二进制缓存工厂
 */
public class BinaryJedisFactory {

    protected static Logger logger = LoggerFactory.getLogger(BinaryJedisFactory.class);

    private static IBinaryJedis JEDIS_CACHE = null;

    public static IBinaryJedis getJedis() {
        if (JEDIS_CACHE != null) {
            return JEDIS_CACHE;
        }
        RedisPoolConfig redisPoolConfig = SpringBeanUtils.getBean(RedisPoolConfig.class);
        if (redisPoolConfig == null) {
            return null;
        }
        JEDIS_CACHE = new BinaryJedisPool(redisPoolConfig);
        return JEDIS_CACHE;
    }

}
