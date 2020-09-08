package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableEurekaClient //不需要显示的加入注解，引入相应依赖后就会自动生效
@EnableFeignClients(basePackages = "com.example")
@SpringBootApplication(scanBasePackages = { "com.example.demo", "com.example.pubserv" })
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
