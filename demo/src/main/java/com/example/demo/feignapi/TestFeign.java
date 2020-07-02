package com.example.demo.feignapi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by new on 2020/7/2.
 */
@FeignClient(name = "demo", path = "/test")
public interface TestFeign {

    @GetMapping(value = "/hello")
    String hello();

}
