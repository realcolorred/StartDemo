package com.example.demo.util.redis;

import com.example.demo.util.PropertyUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by realcolorred on 2019/3/13.
 */
public class BinaryJedisCluster extends BaseBinaryJedis {

    private redis.clients.jedis.BinaryJedisCluster jedisCluster;

    public BinaryJedisCluster(Properties props) {
        JedisPoolConfig jpc = getConfig(props);

        Set<HostAndPort> nodes = new HashSet<>();
        int nodeSize = PropertyUtil.getProperty(props, "nodes", 0);// 节点数
        for (int i = 1; i <= nodeSize; ++i) {// 从1开始配置
            String hostport = PropertyUtil.getProperty(props, "hostport-" + i, "localhost:6379");
            nodes.add(HostAndPort.parseString(hostport));
        }

        int connectionTimeout = PropertyUtil.getProperty(props, "timeout", Protocol.DEFAULT_TIMEOUT);
        int soTimeout = PropertyUtil.getProperty(props, "sotimeout", Protocol.DEFAULT_TIMEOUT);
        int maxRedirections = PropertyUtil.getProperty(props, "max-redirections", 5);
        maxRedirections = Math.min(nodeSize, maxRedirections);
        String password = PropertyUtil.getProperty(props, "password", null);

        this.jedisCluster = new redis.clients.jedis.BinaryJedisCluster(nodes, connectionTimeout, soTimeout, maxRedirections, password, jpc);
    }

    @Override
    public Long del(byte[] key) {
        return jedisCluster.del(key);
    }

    @Override
    public Boolean exists(byte[] key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        return jedisCluster.expireAt(key, unixTime);
    }

    @Override
    public Long persist(byte[] key) {
        return jedisCluster.persist(key);
    }

    @Override
    public Long ttl(byte[] key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public String type(byte[] key) {
        return jedisCluster.type(key);
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public byte[] get(byte[] key) {
        return jedisCluster.get(key);
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        return jedisCluster.setex(key, seconds, value);
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        return jedisCluster.setnx(key, value);
    }

    @Override
    public Long strlen(byte[] key) {
        return jedisCluster.strlen(key);
    }

}
