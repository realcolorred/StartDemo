package com.example.demo.util;

import com.example.demo.constants.SysConstants;
import com.example.demo.util.redis.BinaryJedisFactory;
import com.example.demo.constants.RedisConstans;
import com.example.demo.util.redis.command.IBinaryJedis;

import java.io.UnsupportedEncodingException;

/**
 * Created by lenovo on 2019/3/13.
 */
public class RedisUtil {

    private static IBinaryJedis binaryJedis;

    private static IBinaryJedis getBinaryJedis() {
        if (binaryJedis != null) {
            return binaryJedis;
        }
        synchronized (IBinaryJedis.class) {
            if (binaryJedis != null) {
                return binaryJedis;
            }
            // 初始化
            binaryJedis = BinaryJedisFactory.getJedis(RedisConstans.REDIS_CONFIG);
        }
        if (binaryJedis == null) {
            throw new RuntimeException("获取缓存失败:不支持的缓存类型" + RedisConstans.REDIS_CONFIG);
        }
        return binaryJedis;
    }

    public static boolean existKey(String key) {
        try {
            return getBinaryJedis().exists(key.getBytes(SysConstants.SYS_ENCODING));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
