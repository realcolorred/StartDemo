package com.example.demoapi.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by new on 2020/7/2.
 */
@FeignClient(name = "demo", path = "/metr", url = "${demo-url:}")
public interface TestFeign {

    @GetMapping
    ResponseEntity<String> query();

}
