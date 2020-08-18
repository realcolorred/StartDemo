package com.example.pubserv.util.redis;

import com.example.pubserv.constant.RedisConstant;
import com.example.pubserv.util.redis.command.IBinaryJedis;
import com.example.pubserv.util.redis.factory.BinaryJedisFactory;

/**
 * Created by lenovo on 2019/3/13.
 */
public class RedisUtil {

    private static IBinaryJedis binaryJedis;

    private static final String ENCODING = "UTF-8";

    private static IBinaryJedis getBinaryJedis() {
        if (binaryJedis != null) {
            return binaryJedis;
        }
        synchronized (IBinaryJedis.class) {
            if (binaryJedis != null) {
                return binaryJedis;
            }
            // 初始化
            binaryJedis = BinaryJedisFactory.getJedis();
        }
        if (binaryJedis == null) {
            throw new RuntimeException("获取缓存失败:不支持的缓存类型");
        }
        return binaryJedis;
    }

    public static boolean existKey(String key) {
        try {
            return getBinaryJedis().exists(key.getBytes(ENCODING));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
