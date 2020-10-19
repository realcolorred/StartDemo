package com.example.demoapi.api;

import com.example.common.request.ApiRespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by new on 2020/10/1.
 */
@FeignClient(name = "demo", contextId = "servant", path = "/servant", url = "${demo-url:}")
public interface ServantApi {

    @PostMapping(value = "/insert")
    public ApiRespResult<String> insert(@RequestParam(value = "name") String name);

    @PostMapping(value = "/insertData")
    public ApiRespResult<String> insertData(@RequestParam(value = "name") String name, @RequestParam(value = "ename") String ename,
                                            @RequestParam(value = "sex") String sex, @RequestParam(value = "weight") String weight,
                                            @RequestParam(value = "height") String height, @RequestParam(value = "star") String star);
}
