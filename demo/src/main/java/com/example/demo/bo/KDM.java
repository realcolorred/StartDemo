package com.example.demo.bo;

import lombok.Data;

/**
 * Created by lenovo on 2019/9/19.
 *
 * 密钥传送消息（Key Delivery Message）简称KDM
 */
@Data
public class KDM {
    private String pubStr;
    private String priStr;
    private String sign;
}
