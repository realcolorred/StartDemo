package com.example.demo.controller;

import com.example.demoapi.api.TestFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengli
 */
@Api(description = "feign调用测试")
@RestController
@RequestMapping("/feign")
public class FeignTestController {

    @Autowired(required = false)
    private TestFeign testFeign;

    @ApiOperation("测试")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return testFeign.query();
    }
}
