package com.example.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * Created by new on 2020/7/18.
 */
@Configuration
public class RequestRateLimiterConfig {

    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
    }

    @Bean
    public KeyResolver urlKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }

}
