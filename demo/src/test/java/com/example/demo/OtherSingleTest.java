package com.example.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OtherSingleTest {

    @BeforeClass
    public static void beforeTest() {
        System.out.println("============测试开始================");
    }

    @Test
    public void test() {
        getData(0.001);
    }

    @AfterClass
    public static void afterTest() {
        System.out.println("============测试结束================");
    }

    private Doudate getData(double minSp) {
        List<Doudate> data = new ArrayList<>();

        double startx = 0;
        double starty = 0;
        double endx = 16;
        double endy = 4;
        for (double x = startx; x < endx; x += minSp) {
            for (double y = starty; y < endy; y += minSp) {
                if ((x - y) > 6 && (x - y) <= 13 && (x + 7 * y) <= 35) {
                    Doudate doudate = new Doudate();
                    doudate.x = x;
                    doudate.y = y;
                    doudate.data = (x + y) / (x - y);
                    data.add(doudate);
                }
            }
        }

        Doudate max = new Doudate();
        for (Doudate d : data) {
            if (d.data > max.data)
                max = d;
        }
        System.out.println("max:" + 126 * max.data + "\nx:" + max.x + "\ny:" + max.y);
        return max;
    }

    class Doudate {
        double x;
        double y;
        double data;
    }
}
