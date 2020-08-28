package com.example.common.exception;

import java.text.MessageFormat;

/**
 * Created by new on 2020/8/28.
 */
public class DemoException extends RuntimeException {

    private String code;

    public DemoException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DemoException(ErrorMessage errorMessage, String... paramValue) {
        super(MessageFormat.format(errorMessage.getMsg(), paramValue));
        this.code = errorMessage.getCode();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
