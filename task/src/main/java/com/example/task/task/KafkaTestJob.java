package com.example.task.task;

import com.example.task.task.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by new on 2020/10/10.
 */
@Slf4j
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class KafkaTestJob extends BaseJob<String> {

    @Value("${spring.demo.job.status.KafkaTestJob:close}")
    private String status;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedRate = 10 * 1000)
    public void test() {
        if ("open".equals(status)) {
            super.execute();
        }
    }

    @Override
    public List<String> getTasks() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("消息测试" + System.currentTimeMillis() + "-" + i);
        }
        return list;
    }

    @Override
    public void doTask(String task) {
        kafkaTemplate.send("KafkaTestJob", task);
    }
}
