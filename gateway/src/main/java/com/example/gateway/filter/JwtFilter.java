package com.example.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.common.constant.LogConstants;
import com.example.common.util.StringUtil;
import com.example.gateway.common.ExceptionEnum;
import com.example.gateway.common.JwtConst;
import com.example.gateway.common.ReturnData;
import com.example.gateway.util.JWTSigner;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("#{'${filter.jwt.ignore}'.split(',')}")
    public List<String> noFilterList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String url = request.getURI().getPath();
        String token = request.getHeaders().getFirst(JwtConst.TOKEN);
        String reqId = request.getHeaders().getFirst(LogConstants.REQUEST_ID);
        String userId = request.getHeaders().getFirst(LogConstants.USER_ID);

        if (StringUtil.isNotEmpty(reqId) || StringUtil.isNotEmpty(userId)) {
            log.warn("非法请求!用户token:{}请求url:{}.输入的reqId:{},userId:{}", token, url, reqId, userId);
            return failResponse(request, response, ExceptionEnum.TOKEN_INVALID);
        }

        if (shouldPass(url)) {
            return chain.filter(exchange);
        }

        if (token != null && !"".equals(token)) {
            try {
                // 验证签名
                DecodedJWT decodedJWT = JWTSigner.verify(token);
                String userInfo = decodedJWT.getClaim(JwtConst.USER_INFO).asString();

                // 也许token需要延期了
                String newToken = JWTSigner.maybeExtend(decodedJWT);
                if (newToken != null) {
                    log.info("用户：{}的token需要更换，新token为：{}", userInfo, newToken);
                    response.getHeaders().add(JwtConst.TOKEN, newToken);
                }

                // 用户信息写入请求头
                request.mutate().header(JwtConst.USER_INFO, userInfo).build();

                log.info("登录用户信息为：{}，请求url为：{}", userInfo, url);
                return chain.filter(exchange.mutate().request(request).build());
            } catch (TokenExpiredException e) {
                log.info("jwt expired", e);
                return failResponse(request, response, ExceptionEnum.TOKEN_EXPIRED);
            } catch (Exception e) {
                log.info("jwt verify failed", e);
                return failResponse(request, response, ExceptionEnum.TOKEN_INVALID);
            }
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
