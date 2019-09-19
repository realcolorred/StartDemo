package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lenovo on 2019/9/19.
 *
 * 密钥传送消息（Key Delivery Message）简称KDM
 */
@Getter
@Setter
@ToString
public class KDM {
    private String pubStr;
    private String priStr;
    private String sign;
}
