package com.example.common.exception;

import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * Created by new on 2020/8/28.
 */
public enum ErrorMessage {

    REQUEST_FAIL("EX500", "请求失败"),
    REQUEST_PARAM_ERROR("EX501", "参数错误：{0}"),
    REQUEST_NOFOUND_ERROR("EX502", "方法[{0}.{1}]不存在或参数错误!"),

    DATA_INSERT_FAIL("EX600", "新增失败,{0}!"),
    DATA_INSERT_FAIL_EXIST("EX601", "新增失败,数据[{0}]已经存在!"),

    USER_LOGIN_FAIL("EX700", "登录失败，未知错误"),
    USER_LOGIN_WRONG("EX701", "登录失败，用户名或密码错误"),
    USER_LOGIN_BLACK("EX702", "登录失败，用户名或密码为空"),
    USER_LOGIN_FORBID("EX703", "登录失败，该用户禁止登录"),

    UNKNOW_ERROR("EX9999", "未知错误");

    private String code;
    private String msg;

    ErrorMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
