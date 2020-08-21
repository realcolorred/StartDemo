package com.example.demo;

import com.example.demo.config.DataSourceCompanyConfig;
import com.example.pubserv.util.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "com.example.demo", "com.example.pubserv" })
//@EnableEurekaClient //不需要显示的加入注解，引入相应依赖后就会自动生效
@EnableFeignClients(basePackages = "com.example")
public class DemoApplication {

    protected static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        DataSourceCompanyConfig companyConfig = SpringBeanUtils.getBean(DataSourceCompanyConfig.class);
        //logger.info(companyConfig.toString());
    }
}
