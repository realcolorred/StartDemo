package com.example.gateway.common;

import org.springframework.http.HttpStatus;

/**
 * Created by new on 2020/7/18.
 */
public enum ExceptionEnum {
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "未登录"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "登录过期"),
    TOKEN_LOGOUT(HttpStatus.UNAUTHORIZED, "用户已登出"),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "验证失败，禁止访问"),
    TOKEN_NO_PRIVILEGE(HttpStatus.FORBIDDEN, "没有访问权限"),
    GATEWAY_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "网关错误"),
    TICKET_VALIDATE_ERROR(HttpStatus.UNAUTHORIZED, "Ticket验证失败");

    private HttpStatus status;
    private String     message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
