package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by zl on 2020/8/6.
 *
 * 题目描述
 * 问题描述：数独（Sudoku）是一款大众喜爱的数字逻辑游戏。
 * 玩家需要根据9X9盘面上的已知数字，推算出所有剩余空格的数字，并且满足每一行、每一列、每一个粗线宫内的数字均含1-9，并且不重复。
 * 输入：
 * 包含已知数字的9X9盘面数组[空缺位以数字0表示]
 * 输出：
 * 完整的9X9盘面数组
 *
 */
public class JavaTest3 {

    public static void main(String[] ages) {
        test1();
    }

    public static void test1() {
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNext()) {
                int[][] qiPan = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    String input = s.nextLine();
                    String[] strs = input.split(" ");
                    for (int j = 0; j < 9; j++) {
                        qiPan[i][j] = Integer.parseInt(strs[j]);
                    }
                }

                long start = System.currentTimeMillis();
                doIt(qiPan);
                long end = System.currentTimeMillis();
                System.out.println("计算花费时间" + (end - start) + "ms");

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (j == 0 && i != 0) {
                            System.out.println();
                            System.out.print(qiPan[i][j]);
                        } else if (j == 0) {
                            System.out.print(qiPan[i][j]);
                        } else {
                            System.out.print(" " + qiPan[i][j]);
                        }
                    }
                }
            }
        }
    }

    public static void doIt(int[][] qiPan) {
        List<Map<String, Object>> stepMap = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (qiPan[i][j] == 0) {
                    Map<String, Object> step = new HashMap<>();
                    step.put("x", i);
                    step.put("y", j);
                    step.put("value", 0);
                    stepMap.add(step);
                }
            }
        }

        for (int stepInt = 0; stepInt < stepMap.size(); ) {
            if (fill(qiPan, stepInt, stepMap)) {
                stepInt++;
            } else {
                stepInt--;
                cancel(qiPan, stepInt, stepMap);
            }
        }
        //System.out.println("行走全路径为" + stepMap);
    }

    public static boolean fill(int[][] qiPan, int setpInt, List<Map<String, Object>> stepMap) {
        Map<String, Object> stepInfo = stepMap.get(setpInt);
        int x = (int) stepInfo.get("x");
        int y = (int) stepInfo.get("y");
        Set<Integer> choose;
        if (stepInfo.containsKey("choose")) {
            choose = (Set<Integer>) stepInfo.get("choose");
        } else {
            choose = getChooseNumber(qiPan, x, y);
        }

        if (choose.isEmpty()) {
            return false;
        }

        int fillValue = (int) choose.toArray()[0];
        stepInfo.put("value", fillValue);
        stepInfo.put("choose", choose);
        qiPan[x][y] = fillValue;
        System.out.println("第" + setpInt + "部填充成功，坐标" + x + " " + y + "，选值为" + fillValue + "，当前可选值有" + choose);
        return true;
    }

    public static void cancel(int[][] qiPan, int setpInt, List<Map<String, Object>> stepMap) {
        Map<String, Object> stepInfo = stepMap.get(setpInt);
        int x = (int) stepInfo.get("x");
        int y = (int) stepInfo.get("y");
        int value = (int) stepInfo.get("value");
        Set<Integer> choose = (Set<Integer>) stepInfo.get("choose");

        choose.remove(value);
        stepInfo.put("value", 0);
        qiPan[x][y] = 0;

        if (setpInt + 1 < stepMap.size()) {
            Map<String, Object> nextStep = stepMap.get(setpInt + 1);
            if (nextStep.containsKey("choose")) {
                nextStep.remove("choose");
            }
        }
        System.out.println("撤销第" + setpInt + "步的选值" + value + "剩余的可选值有" + choose);
    }

    public static Set<Integer> getChooseNumber(int[][] qiPan, int x, int y) {
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (int i = 0; i < 9; i++) {
            int value = qiPan[x][i];
            if (value != 0 && set.contains(value)) {
                set.remove(value);
            }
        }
        for (int j = 0; j < 9; j++) {
            int value = qiPan[j][y];
            if (value != 0 && set.contains(value)) {
                set.remove(value);
            }
        }
        if (x >= 0 && x <= 2)
            x = 0;
        if (x >= 3 && x <= 5)
            x = 3;
        if (x >= 6 && x <= 8)
            x = 6;
        if (y >= 0 && y <= 2)
            y = 0;
        if (y >= 3 && y <= 5)
            y = 3;
        if (y >= 6 && y <= 8)
            y = 6;
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                int value = qiPan[i][j];
                if (value != 0 && set.contains(value)) {
                    set.remove(value);
                }
            }
        }
        return set;
    }
}
