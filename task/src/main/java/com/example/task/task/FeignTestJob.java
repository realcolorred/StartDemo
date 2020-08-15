package com.example.task.task;

import com.example.demoapi.api.TestFeign;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by new on 2020/8/15.
 */
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class FeignTestJob extends BaseJob {

    @Autowired
    private TestFeign testFeign;

    @Scheduled(fixedRate = 5 * 1000)//每5秒执行一次
    public void sadasd() {
        try {
            testFeign.query();
            logger.info("请求调用demo服务的api成功。");
        } catch (Exception e) {
            logger.error("请求调用demo服务的api失败！", e);
        }
    }
}
