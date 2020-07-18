package com.example.gateway.filter;

import com.example.gateway.common.ExceptionEnum;
import com.example.gateway.common.ReturnData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by new on 2020/7/18.
 */
@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("#{'${filter.jwt.ignore}'.split(',')}")
    public List<String> noFilterList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();

        if (shouldPass(url)) {
            return chain.filter(exchange);
        }

        return failResponse(request, response, ExceptionEnum.TOKEN_EMPTY);
    }

    private Mono<Void> failResponse(ServerHttpRequest request, ServerHttpResponse response, ExceptionEnum tokenInvalid) {
        response.setStatusCode(tokenInvalid.getStatus());
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String returnStr = ReturnData.getErrorReturnString(tokenInvalid);
        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

    private boolean shouldPass(String url) {
        if (CollectionUtils.isEmpty(this.noFilterList)) {
            return true;
        }
        PathMatcher antPathMatcher = new AntPathMatcher();
        return this.noFilterList.stream().anyMatch(s -> antPathMatcher.match(s, url));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
