package com.example.pubserv.util.redis.inst;

import com.example.pubserv.config.RedisPoolConfig;
import com.example.pubserv.util.redis.command.IBinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

/**
 * Created by lenovo on 2019/3/12.
 *
 * pool
 */
public class BinaryJedisPool extends BaseBinaryJedis implements IBinaryJedis {

    private Pool<Jedis> jedisPool; // 连接池

    public BinaryJedisPool(RedisPoolConfig redisPoolConfig) {
        this.jedisPool = initJedisPool(redisPoolConfig);
    }

    protected Pool<Jedis> initJedisPool(RedisPoolConfig redisPoolConfig) {
        JedisPoolConfig jpc = getConfig(redisPoolConfig);
        String host = getValue(redisPoolConfig.getHost(), Protocol.DEFAULT_HOST);
        int port = getValue(redisPoolConfig.getPort(), Protocol.DEFAULT_PORT);
        int timeout = getValue(redisPoolConfig.getTimeout(), Protocol.DEFAULT_TIMEOUT);
        String password = getValue(redisPoolConfig.getPassword(), null);
        int database = getValue(redisPoolConfig.getDatabase(), Protocol.DEFAULT_DATABASE);
        String clientName = getValue(redisPoolConfig.getClientName(), null);
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
