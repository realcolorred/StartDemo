package com.example.demo.task;

import com.example.demo.util.DateHelper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lenovo on 2019/9/12.
 */
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class TimeJob extends BaseJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("测试定时任务启动,现在的时间是" + DateHelper.getCurrentTimeRead());
    }
}
