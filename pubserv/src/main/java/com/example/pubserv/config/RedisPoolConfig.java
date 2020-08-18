package com.example.pubserv.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by new on 2020/8/18.
 */
@Component
@Getter
public class RedisPoolConfig {

    @Value("${jedis.pool.host:127.0.0.1}")
    public String host;

    @Value("${jedis.pool.port:6379}")
    public String port;

    @Value("${jedis.pool.timeout:}")
    public String timeout;

    @Value("${jedis.pool.password:}")
    public String password;

    @Value("${jedis.pool.database:}")
    public String database;

    @Value("${jedis.pool.clientName:}")
    public String clientName;

}
