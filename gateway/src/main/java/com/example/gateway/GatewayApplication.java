package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "com.example.gateway", "com.example.pubserv" })
@EnableFeignClients(basePackages = "com.example")
//@EnableEurekaClient //不需要显示的加入注解，引入相应依赖后就会自动生效
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
