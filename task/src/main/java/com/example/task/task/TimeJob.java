package com.example.task.task;

import com.example.common.util.DateUtil;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lenovo on 2019/9/12.
 */
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class TimeJob extends BaseJob {

    //@Scheduled(fixedRate = 5 * 1000)//每5秒执行一次
    public void work() {
        logger.info("测试定时任务启动,现在的时间是" + DateUtil.dateToStringRead(new Date()));
    }

    //@Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void work2() {
        logger.info("一分钟一次的定时任务测试,现在的时间是" + DateUtil.dateToStringRead(new Date()));
    }
}
