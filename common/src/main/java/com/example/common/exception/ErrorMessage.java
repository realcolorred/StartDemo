package com.example.common.exception;

/**
 * Created by new on 2020/8/28.
 */
public enum ErrorMessage {

    REQUEST_FAIL("EX500", "请求失败"),
    REQUEST_PARAM_ERROR("EX501", "参数错误：{0}"),
    REQUEST_NOFOUND_ERROR("EX502", "方法{0}.{1}不存在或参数错误！");

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
