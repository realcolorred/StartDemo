package com.example.task.kafkalistener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by new on 2020/10/10.
 */
@Slf4j
@Component
public class TestListener {

    @KafkaListener(topics = { "KafkaTestJob" })
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        try {
            //Thread.sleep(50);
            log.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
