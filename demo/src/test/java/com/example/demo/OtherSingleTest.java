package com.example.demo;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OtherSingleTest {

    @BeforeClass
    public static void beforeTest() {
        //System.out.println("============测试开始================");
    }

    @AfterClass
    public static void afterTest() {
        //System.out.println("============测试结束================");
    }

    @Test
    public void metricsTest() {
        Metrics.addRegistry(new SimpleMeterRegistry());
        Timer timer = Metrics.timer("method.cost.time", "method.name", "测试");
        timer.record(23, TimeUnit.MILLISECONDS);
        timer.record(25, TimeUnit.MILLISECONDS);
        timer.record(33, TimeUnit.MILLISECONDS);
        timer.record(65, TimeUnit.MILLISECONDS);
        timer.record(12, TimeUnit.MILLISECONDS);
        System.out.print(timer.measure());
    }

    @Test
    public void timeTest() {
        String[] strs = { "s", "m", "h" };
        for (String str : strs) {
            for (int i = 1; i < 100; i++) {
                System.out.print(repeatIntervalCheck(i + str));
            }
        }

    }

    /**
     * 告警频率检查
     * @param repeatInterval 时间: 数字 + s/m/h。 如 1s，20s，5m，6h
     */
    private String repeatIntervalCheck(String repeatInterval) {
        if ("120m".equals(repeatInterval)) {
            return "2h";
        }
        if (repeatInterval.matches("[1-9][0-9]{0,2}[smh]")) {
            return repeatInterval;
        } else {
            throw new RuntimeException("告警频率当前填写值：" + repeatInterval + " 格式错误！");
        }
    }

    @Test
    public void java8DateTest() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }

    @Test
    public void test() {
        String str = "我的天啥,这是啥,为啥,你傻傻的沙雕阿萨德阿萨德啊是打的阿萨德,阿萨德阿萨德阿萨德阿萨德,啊实打实的";
        List<String> list = Arrays.asList(str.split(","));
        long count = list.stream().filter(w -> w.length() > 5).count();
        System.out.println(count);
    }

}
