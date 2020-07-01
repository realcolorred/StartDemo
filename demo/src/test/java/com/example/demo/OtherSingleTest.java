package com.example.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherSingleTest {

    @BeforeClass
    public static void beforeTest() {
        System.out.println("============测试开始================");
    }

    @AfterClass
    public static void afterTest() {
        //System.out.println("============测试结束================");
    }

    @Test
    public void java8DateTest(){
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }

    @Test
    public void test() {
        String str = "我的天啥,这是啥,为啥,你傻傻的沙雕阿萨德阿萨德啊是打的阿萨德,阿萨德阿萨德阿萨德阿萨德,啊实打实的";
        List<String> list = Arrays.asList(str.split(","));
        long count = list.stream().filter(w -> w.length()> 5).count();
        System.out.println(count);
    }
}
