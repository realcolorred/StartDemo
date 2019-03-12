package com.example.demo.util.redis;

import com.example.demo.util.redis.command.IBinaryJedis;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import redis.clients.jedis.BinaryJedisCluster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
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
        Properties props = getProperties(redisKey + ".properties");
        if (props == null || props.isEmpty()) {
            return null;
        }
        return initJedis(redisKey, props);
    }

    private static IBinaryJedis initJedis(String redisKey, Properties props) {
        IBinaryJedis jedis = null;
        synchronized (jedisCache) {
            jedis = jedisCache.get(redisKey);
            if (jedis != null) {
                return jedis;
            }

            String jedisClass = props.getProperty("jedis-class", "single"); // 缓存类型
            if ("cluster".equals(jedisClass)) {

            } else if ("single".equals(jedisClass)) {
                jedis = new BinaryJedisPool(getProperties(props, "jedis-single."));
            } else {
                return null;
            }
            jedisCache.put(redisKey, jedis);
        }
        return jedis;
    }

    public static Properties getProperties(String redisCfg) {
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(redisCfg);
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                return props;
            }
        } catch (IOException ignore) {
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    protected static Properties getProperties(Properties props, String prefix) {
        Properties new_props = new Properties();
        Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix)) {
                new_props.setProperty(key.substring(prefix.length()), props.getProperty(key));
            }
        }
        return new_props;
    }

}
