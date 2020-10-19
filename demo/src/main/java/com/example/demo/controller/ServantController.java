package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.exception.DemoException;
import com.example.common.request.ApiRespResult;
import com.example.common.util.NumberUtil;
import com.example.demo.dao.sourceCompany.entity.Servant;
import com.example.demo.service.IServantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zl
 */
@Slf4j
@RestController
@RequestMapping("/servant")
public class ServantController {

    @Autowired
    private IServantService servantService;

    @PostMapping(value = "/insertOneTestData")
    public ApiRespResult<Integer> insertOneTestData() {
        int ret = servantService.insertServant("我是一个测试");
        return ApiRespResult.success(ret);
    }

    @PostMapping(value = "/insert")
    public ApiRespResult<String> insert(@RequestParam(value = "name") String name) {
        try {
            servantService.insertServant(name);
        } catch (DemoException e) {
            log.warn(e.getMessage());
            return ApiRespResult.fail(e.getCode(), e.getMessage());
        }
        return ApiRespResult.success(name);
    }

    @PostMapping(value = "/insertData")
    public ApiRespResult<String> insertData(@RequestParam(value = "name") String name, @RequestParam(value = "ename") String ename,
                                            @RequestParam(value = "sex") String sex, @RequestParam(value = "weight") String weight,
                                            @RequestParam(value = "height") String height, @RequestParam(value = "star") String star) {
        try {
            Servant servant = new Servant();
            servant.setServantNameChina(name);
            servant.setServantNameEnglish(ename);
            servant.setSex(sex);
            servant.setWeight(weight);
            servant.setHeight(height);
            servant.setStar(NumberUtil.toLong(star));
            servantService.insertServant(servant);
        } catch (DemoException e) {
            log.warn(e.getMessage());
            return ApiRespResult.fail(e.getCode(), e.getMessage());
        }
        return ApiRespResult.success(name);
    }

    @GetMapping("getList")
    public ApiRespResult<Page<Servant>> getList(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "eName", required = false) String eName,
                                                @RequestParam(value = "sex", required = false) String sex,
                                                @RequestParam(value = "star", required = false) Long star,
                                                @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Long pageIndex,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize) {
        return ApiRespResult.success(servantService.getList(name, eName, sex, star, pageIndex, pageSize));
    }
}
