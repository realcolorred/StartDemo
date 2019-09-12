package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyResponseBean {

    private int    code;
    private String message;
    private Object data;

    public MyResponseBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyResponseBean(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
