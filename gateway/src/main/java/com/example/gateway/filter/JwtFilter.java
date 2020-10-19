package com.example.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.common.constant.LogConstants;
import com.example.common.exception.DemoException;
import com.example.common.request.ApiRespResult;
import com.example.common.util.StringUtil;
import com.example.demoapi.api.UserApi;
import com.example.demoapi.dto.UserInfoDto;
import com.example.gateway.common.ExceptionEnum;
import com.example.gateway.common.JwtConst;
import com.example.gateway.common.ReturnData;
import com.example.gateway.util.JWTSigner;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<String> noFilterList;

    @Autowired
    private UserApi userApi;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String url = request.getURI().getPath();
        String token = request.getHeaders().getFirst(JwtConst.TOKEN);
        String reqId = request.getHeaders().getFirst(LogConstants.REQUEST_ID);
        String userId = request.getHeaders().getFirst(LogConstants.USER_ID);

        // 用户自己不能自动传入reqId，userId等误导信息干扰程序判断。
        if (StringUtil.isNotEmpty(reqId) || StringUtil.isNotEmpty(userId)) {
            log.warn("非法请求!用户token:{}请求url:{}.输入的reqId:{},userId:{}", token, url, reqId, userId);
            return failResponse(request, response, ExceptionEnum.TOKEN_INVALID);
        }

        // 某些请求并不需要登录
        if (shouldPass(url)) {
            return chain.filter(exchange);
        }

        if (StringUtil.isNotEmpty(token)) {
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

                if (!canVisit(userInfo, url)) {
                    log.warn("用户越权访问。用户信息为：{}，请求url为：{}", userInfo, url);
                    return failResponse(request, response, ExceptionEnum.TOKEN_NO_PRIVILEGE);
                }

                return chain.filter(exchange.mutate().request(request).build());
            } catch (TokenExpiredException e) {
                log.info("jwt expired, {} token:{}", e.getMessage(), token);
                return failResponse(request, response, ExceptionEnum.TOKEN_EXPIRED);
            } catch (Exception e) {
                log.warn("jwt verify failed", e);
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

    private boolean canVisit(String userInfo, String url) throws Exception {
        UserInfoDto userInfoDto = JSON.parseObject(userInfo, UserInfoDto.class);
        ApiRespResult<List<String>> apiRespResult = userApi.getPermission(userInfoDto.getUserId());
        if (apiRespResult.isSuccess()) {
            PathMatcher antPathMatcher = new AntPathMatcher();
            return apiRespResult.getData().stream().anyMatch(s -> antPathMatcher.match(s, url));
        } else {
            throw new Exception("获取用户权限失败，禁止访问！");
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
