package com.example.gateway.common;

import com.alibaba.fastjson.JSON;

/**
 * Created by new on 2020/7/18.
 */
public class ReturnData<T> {

    private int    code;
    private String message;

    public ReturnData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getErrorReturnString(ExceptionEnum e) {
        ReturnData<String> returnData = new ReturnData<>(e.getStatus().value(), e.getMessage());
        String returnStr = "";
        returnStr = JSON.toJSONString(returnData);
        return returnStr;
    }

}
