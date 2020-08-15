package com.example.demo.util.redis.inst;

import com.example.demo.util.redis.command.IBinaryJedis;
import com.example.pubserv.util.PropertyUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

import java.util.Properties;

/**
 * Created by lenovo on 2019/3/12.
 *
 * pool
 */
public class BinaryJedisPool extends BaseBinaryJedis implements IBinaryJedis {

    private Pool<Jedis> jedisPool; // 连接池

    public BinaryJedisPool(Properties props) {
        this.jedisPool = initJedisPool(props);
    }

    protected Pool<Jedis> initJedisPool(Properties props) {
        JedisPoolConfig jpc = getConfig(props);
        String host = PropertyUtil.getProperty(props, "host", Protocol.DEFAULT_HOST);
        int port = PropertyUtil.getProperty(props, "port", Protocol.DEFAULT_PORT);
        int timeout = PropertyUtil.getProperty(props, "timeout", Protocol.DEFAULT_TIMEOUT);
        String password = PropertyUtil.getProperty(props, "password", null);
        int database = PropertyUtil.getProperty(props, "database", Protocol.DEFAULT_DATABASE);
        String clientName = PropertyUtil.getProperty(props, "client-name", null);
        return new JedisPool(jpc, host, port, timeout, password, database, clientName);
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            return jedis.setnx(key, value);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        Jedis jedis = getJedis();
        try {
            return jedis.expire(key, seconds);
        } finally {
            close(jedis);
        }
    }

    @Override
    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.del(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Boolean exists(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        Jedis jedis = getJedis();
        try {
            return jedis.expireAt(key, unixTime);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long persist(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.persist(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long ttl(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.ttl(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public String type(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.type(key);
        } finally {
            close(jedis);
        }
    }

    @Override
    public String set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            return jedis.set(key, value);
        } finally {
            close(jedis);
        }
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        Jedis jedis = getJedis();
        try {
            return jedis.setex(key, seconds, value);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Long strlen(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.strlen(key);
        } finally {
            close(jedis);
        }
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
