package com.example.gateway.fallback;

import com.example.gateway.common.ExceptionEnum;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by new on 2020/7/18.
 */
@RestController
@RequestMapping("/fallback")
public class HystrixFallbackController {

    @RequestMapping("/default")
    public Mono<Map<String, String>> defaultfallback(ServerWebExchange exchange) {
        Throwable thr = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        Map<String, String> map = new HashMap<>();
        map.put("code", String.valueOf(ExceptionEnum.GATEWAY_ERROR.getStatus().value()));
        map.put("message", thr.getMessage());
        map.put("exception", "stack trace:" + ExceptionUtils.getStackTrace(thr));
        return Mono.just(map);
    }

}
