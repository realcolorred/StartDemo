package com.example.demo.task;

import com.example.demo.util.DateHelper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lenovo on 2019/9/12.
 */
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class TimeJob extends BaseJob {

    @Scheduled(fixedRate = 5000)//每5秒执行一次
    public void work() throws Exception {
        logger.info("测试定时任务启动,现在的时间是" + DateHelper.dateToString(new Date(), DateHelper.YYYYMMDDHHMMSS_READ));
    }

    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void work2() throws Exception {
        logger.info("一分钟一次的定时任务测试,现在的时间是" + DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSS_read));
    }
}
