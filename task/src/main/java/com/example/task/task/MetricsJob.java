package com.example.task.task;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by new on 2020/8/25.
 */
@Slf4j
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class MetricsJob extends BaseJob<String> {

    static {
        Metrics.addRegistry(new SimpleMeterRegistry());
    }

    //@Scheduled(fixedRate = 1 * 1000)//每5秒执行一次
    public void exe() {
        super.execute();
    }

    @Override
    public List<String> getTasks() {
        return Collections.singletonList("metrics");
    }

    @Override
    public void doTask(String task) {
        counterTest();

        Search.in(Metrics.globalRegistry).meters().forEach(each -> {
            StringBuilder builder = new StringBuilder();
            builder.append("name:").append(each.getId().getName()).append(",tags:").append(each.getId().getTags()).append(",type:")
                .append(each.getId().getType()).append(",value:").append(each.measure());
            log.info(builder.toString());
        });
    }

    public void counterTest() {
        Counter counter = Metrics.counter("metrics.test.counter", "from", "MetricsJob");
        counter.increment();
    }
}
