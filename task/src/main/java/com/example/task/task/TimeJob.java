package com.example.task.task;

import com.example.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lenovo on 2019/9/12.
 */
@Slf4j
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class TimeJob {

    @Value("${spring.demo.job.status.TimeJob:close}")
    private String status;

    @Scheduled(fixedRate = 5 * 1000)//每5秒执行一次
    public void work() {
        if ("open".equals(status)) {
            log.info("测试定时任务启动,现在的时间是" + DateUtil.dateToStringRead(new Date()));
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void work2() {
        if ("open".equals(status)) {
            log.info("一分钟一次的定时任务测试,现在的时间是" + DateUtil.dateToStringRead(new Date()));
        }
    }
}
