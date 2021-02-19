package com.example.demo.game;

/**
 * Created by new on 2021/1/20.
 */
public class GailvTest {

    public static int    count = 20; // 抽卡次数
    public static double gailv = 0.006; // 单一出货概率

    public static void main(String[] args) {
        for (int i = 0; i <= count; i++) {
            double temp = Math.pow(gailv, i) * Math.pow(1 - gailv, count - i) * recursion(count) / recursion(i) / recursion(count - i);
            System.out.println(count + "次抽卡中出货" + i + "个的概率为：" + temp);
        }
    }

    public static long recursion(int num) {//利用递归计算阶乘
        long sum = 1;
        if (num < 0)
            throw new IllegalArgumentException("必须为正整数!");//抛出不合理参数异常
        if (num == 1 || num == 0) {
            return 1;//根据条件,跳出循环
        } else {
            sum = num * recursion(num - 1);//运用递归计算
            return sum;
        }
    }

}
