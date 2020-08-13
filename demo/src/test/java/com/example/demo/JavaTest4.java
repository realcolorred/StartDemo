package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by zl on 2020/8/7.
 *
 * 售票问题
 *
 * 1 用户不可指定座位，由系统指定
 * 2 尽可能使得座位利用率最高（即空置座位时间最短）
 *
 */
public class JavaTest4 {

    public static void main(String[] ages) {
        test1();
    }

    public static void test1() {
        try (Scanner s = new Scanner(System.in)) {
            System.out.println("所有站点：" + STATIONS);
            printMap();
            while (s.hasNext()) {

                String input = s.nextLine();
                if ("END".equals(input)) {
                    break;
                }

                //long start = System.currentTimeMillis();
                doIt(input.split(" ")[0], input.split(" ")[1]);
                //long end = System.currentTimeMillis();
                //System.out.println("计算花费时间" + (end - start) + "ms");

                printMap();
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i < STATIONS.size(); i++) {
            if (i == 0) {
                System.out.print(" " + STATIONS.get(i) + " ");
            } else {
                System.out.print("  " + STATIONS.get(i) + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                String value = map[i][j];
                if (EMPTY.equals(value)) {
                    value = "EMP";
                }

                if (j == 0 && i != 0) {
                    System.out.println();
                    System.out.print(value);
                } else if (j == 0) {
                    System.out.print(value);
                } else {
                    System.out.print(" " + value);
                }
            }
        }
        System.out.println();
    }

    // 座位
    public static List<String> SEATS    = Arrays.asList("A1", "A2", "A3");
    // 站点
    public static List<String> STATIONS = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L");

    public static String               EMPTY          = "EMPTY";
    public static String[][]           map            = new String[SEATS.size()][STATIONS.size()];
    public static Map<String, Integer> SEATS_NUM      = new HashMap<>();
    public static Map<Integer, String> SEATS_NUM_I    = new HashMap<>();
    public static Map<String, Integer> STATIONS_NUM   = new HashMap<>();
    public static Map<Integer, String> STATIONS_NUM_I = new HashMap<>();
    public static long                 USER_ID        = 100;

    static {
        for (int i = 0; i < SEATS.size(); i++) {
            for (int j = 0; j < STATIONS.size(); j++) {
                map[i][j] = EMPTY;
            }
        }

        if (!SEATS.isEmpty()) {
            int count = 0;
            for (String temp : SEATS) {
                SEATS_NUM.put(temp, count);
                SEATS_NUM_I.put(count, temp);
                count++;
            }
        }

        if (!STATIONS.isEmpty()) {
            int count = 0;
            for (String temp : STATIONS) {
                STATIONS_NUM.put(temp, count);
                STATIONS_NUM_I.put(count, temp);
                count++;
            }
        }
    }

    public static void doIt(String startStr, String endStr) {
        int start = STATIONS_NUM.get(startStr);
        int end = STATIONS_NUM.get(endStr);
        if (start > end) {
            System.out.println("站点次序错误");
            return;
        } else if (start == end) {
            System.out.println("不允许同站上下");
            return;
        }

        Map<Integer, Float> utilizationRates = new HashMap<>();
        for (int i = 0; i < map.length; i++) {
            boolean canUse = true;
            for (int j = start; j < end; j++) {
                if (!EMPTY.equals(map[i][j])) {
                    canUse = false;
                    break;
                }
            }
            if (canUse) {
                utilizationRates.put(i, getUtilizationRate(i));
            }
        }

        if (utilizationRates.isEmpty()) {
            System.out.println("没有空位了");
        } else {
            int seat = -1;
            float rate = 0;
            for (Integer key : utilizationRates.keySet()) {
                if (seat == -1) {
                    seat = key;
                    rate = utilizationRates.get(key);
                } else if (utilizationRates.get(key) > rate) {
                    seat = key;
                    rate = utilizationRates.get(key);
                }
            }

            for (int i = start; i < end; i++) {
                map[seat][i] = String.valueOf(USER_ID);
            }

            System.out.println("订票成功，你的用户id为" + USER_ID + "，座位为" + SEATS_NUM_I.get(seat));
            USER_ID++;
        }
    }

    public static float getUtilizationRate(int seat) {
        int all = map[0].length - 1; // 终点站必然是空的，不计入计算
        int use = 0;
        for (String state : map[seat]) {
            if (!EMPTY.equals(state)) {
                use++;
            }
        }
        return (float) use / all;
    }
}
