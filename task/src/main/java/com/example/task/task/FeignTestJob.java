package com.example.task.task;

import com.example.demoapi.api.TestFeign;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by new on 2020/8/15.
 */
@Slf4j
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class FeignTestJob extends BaseJob<String> {

    @Autowired
    private TestFeign testFeign;

    //@Scheduled(fixedRate = 5 * 1000)//每5秒执行一次
    public void test() {
        super.execute();
    }

    @Override
    public List<String> getTasks() {
        List<String> jobs = new ArrayList<>();
        for (int i = 1; i < 99; i++) {
            jobs.add("job" + i);
        }
        return jobs;
    }

    @Override
    public void doTask(String task) {
        try {
            testFeign.query();
            Thread.sleep(2000);
            log.info("任务{}执行成功。", task);
        } catch (Exception e) {
            log.error("任务{}执行失败！", task, e);
        }
    }
}
