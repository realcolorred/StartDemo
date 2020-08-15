package com.example.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "com.example.task", "com.example.pubserv" })
//@EnableEurekaClient //不需要显示的加入注解，引入相应依赖后就会自动生效
@EnableScheduling
@EnableFeignClients(basePackages = "com.example")
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

}
