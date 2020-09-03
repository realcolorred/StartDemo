package com.example.demo.controller;

import com.example.demoapi.api.TestFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "feign调用测试")
@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignTestController {

    @Autowired(required = false)
    private TestFeign testFeign;

    @ApiOperation("测试")
    @GetMapping("/test")
    @Trace
    public ResponseEntity<String> test() {
        log.info("tranceId");
        return testFeign.query();
    }
}
