package com.example.demo.controller;

import com.example.demo.feignapi.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengli
 */
@RestController
@RequestMapping("/feign")
public class FeignTestController {

    @Autowired(required = false)
    private TestFeign testFeign;

    @RequestMapping("test")
    public String test() {
        return testFeign.hello();
    }
}
