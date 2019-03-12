package com.example.demo.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lenovo on 2019/3/12.
 *
 * 单点部署的实现
 */
public class BinaryJedisPool extends BaseBinaryJedis {

    private Pool<Jedis> jedisPool;

    public BinaryJedisPool(Properties props) {
        this.jedisPool = initJedisPool(props);
    }

    protected Pool<Jedis> initJedisPool(Properties props) {
        JedisPoolConfig jpc = getConfig(props);
        String host = getProperty(props, "host", Protocol.DEFAULT_HOST);
        int port = getProperty(props, "port", Protocol.DEFAULT_PORT);
        int timeout = getProperty(props, "timeout", Protocol.DEFAULT_TIMEOUT);
        String password = getProperty(props, "password", null);
        int database = getProperty(props, "database", Protocol.DEFAULT_DATABASE);
        String clientName = getProperty(props, "client-name", null);
        return new JedisPool(jpc, host, port, timeout, password, database, clientName);
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
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
    public void close() {
        jedisPool.close();
    }
}
