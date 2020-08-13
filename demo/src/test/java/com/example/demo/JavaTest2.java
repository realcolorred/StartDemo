package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by zl on 2020/8/6.
 *
 * 题目描述
 * 计算24点是一种扑克牌益智游戏，随机抽出4张扑克牌，通过加(+)，减(-)，乘(*), 除(/)四种运算法则计算得到整数24，本问题中，扑克牌通过如下字符或者字符串表示，
 *    其中，小写joker表示小王，大写JOKER表示大王：
 *
 * 3 4 5 6 7 8 9 10 J Q K A 2 joker JOKER
 *
 * 本程序要求实现：输入4张牌，输出一个算式，算式的结果为24点。
 *
 * 详细说明：
 *
 * 1.运算只考虑加减乘除运算，没有阶乘等特殊运算符号，友情提醒，整数除法要当心；
 * 2.牌面2~10对应的权值为2~10, J、Q、K、A权值分别为为11、12、13、1；
 * 3.输入4张牌为字符串形式，以一个空格隔开，首尾无空格；如果输入的4张牌中包含大小王，则输出字符串“ERROR”，表示无法运算；
 * 4.输出的算式格式为4张牌通过+-\*\/ 四个运算符相连，中间无空格，4张牌出现顺序任意，只要结果正确；
 * 5.输出算式的运算顺序从左至右，不包含括号，如1+2+3*4的结果为24
 * 6.如果存在多种算式都能计算得出24，只需输出一种即可，如果无法得出24，则输出“NONE”表示无解。
 *
 */
public class JavaTest2 {

    public static int TARGET_NUMBER = 24; // 通过需要的牌的总点数
    public static int INPUT_SIZE    = 4; // 牌的张数

    public static void main(String[] ages) {
        test2();
    }

    public static void test1() {
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNext()) {
                String input = s.nextLine();
                long start = System.currentTimeMillis();
                doIt(input, true);
                long end = System.currentTimeMillis();
                System.out.println("计算花费时间" + (end - start) + "ms");
            }
        }
    }

    public static void test2() {
        long start = System.currentTimeMillis();
        doIt("4 4 2 7", false);
        long end = System.currentTimeMillis();
        System.out.println("计算花费时间" + (end - start) + "ms");
    }

    public static void test3() {
        List<String> strNumbers = Arrays.asList("2", "3", "3", "Q");
        List<String> opp = Arrays.asList("/", "*", "*");
        long start = System.currentTimeMillis();
        System.out.println(make(strNumbers, opp));
        long end = System.currentTimeMillis();
        System.out.println("计算花费时间" + (end - start) + "ms");
    }

    public static String RESULT_ERROR = "ERROR";
    public static String RESULT_NONE  = "NONE";

    public static String SPLIT = "-";

    public static Map<Integer, String> OP_LIST   = new HashMap<>();
    public static Map<String, Integer> KEY_VALUE = new HashMap<>();

    static {
        KEY_VALUE.put("A", 1);
        KEY_VALUE.put("2", 2);
        KEY_VALUE.put("3", 3);
        KEY_VALUE.put("4", 4);
        KEY_VALUE.put("5", 5);
        KEY_VALUE.put("6", 6);
        KEY_VALUE.put("7", 7);
        KEY_VALUE.put("8", 8);
        KEY_VALUE.put("9", 9);
        KEY_VALUE.put("10", 10);
        KEY_VALUE.put("J", 11);
        KEY_VALUE.put("Q", 12);
        KEY_VALUE.put("K", 13);

        OP_LIST.put(0, "+");
        OP_LIST.put(1, "-");
        OP_LIST.put(2, "*");
        OP_LIST.put(3, "/");
    }

    public static Set<String> doIt(String param, boolean findOneResult) {
        String[] strs = param.split(" ");
        if (strs.length != INPUT_SIZE) {
            System.out.println(RESULT_ERROR);
            return null;
        }
        for (String str : strs) {
            if (!KEY_VALUE.containsKey(str)) {
                System.out.println(RESULT_ERROR);
                return null;
            }
        }

        List<String> asd = listAllCantRepeat(INPUT_SIZE);
        List<String> asd2 = listAllRepeat(OP_LIST.size(), INPUT_SIZE - 1);

        Set<String> resultSet = new HashSet<>();
        int caCount = 0;
        for (String s1 : asd) {
            for (String s2 : asd2) {
                String[] ss1 = s1.split(SPLIT);
                String[] ss2 = s2.split(SPLIT);

                List<String> sdd = new ArrayList<>();
                for (String temp : ss1) {
                    sdd.add(strs[Integer.parseInt(temp)]);
                }

                List<String> opp = new ArrayList<>();
                for (String temp : ss2) {
                    opp.add(OP_LIST.get(Integer.parseInt(temp)));
                }

                caCount++;
                String result;
                if (findOneResult) {
                    result = make(sdd, opp);
                } else {
                    result = make(sdd, opp);
                }

                if (result != null) {
                    if (findOneResult) {
                        System.out.println("经历了" + caCount + "次计算后，找到了一个可行解");
                        System.out.println(result);
                        resultSet.add(result);
                        return resultSet;
                    } else {
                        resultSet.add(result);
                    }
                }
            }
        }

        if (resultSet.size() == 0) {
            System.out.println("经历了" + caCount + "次计算后，还是没有找到解");
            System.out.println(RESULT_NONE);
            return null;
        } else {
            System.out.println("经历了" + caCount + "次计算后，找到了" + resultSet.size() + "个如下解");
            System.out.println(resultSet);
            return resultSet;
        }
    }

    /**
     * 输出排列组合（不可重复）
     * @param size
     * @return
     */
    public static List<String> listAllCantRepeat(int size) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        List<String> resultList = new ArrayList<>();
        listAllCantRepeat(list, "", resultList);
        return resultList;
    }

    public static void listAllCantRepeat(List<Integer> list, String value, List<String> resultList) {
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                List<Integer> newlist = new LinkedList<>(list);
                String newValue = strAdd(value, String.valueOf(newlist.get(i)));
                newlist.remove(i);
                listAllCantRepeat(newlist, newValue, resultList);
            }
        } else {
            String newValue = strAdd(value, String.valueOf(list.get(0)));
            resultList.add(newValue);
        }
    }

    /**
     * 输出排列组合（可重复）
     * @param size
     * @return
     */
    public static List<String> listAllRepeat(int size, int length) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        List<String> resultList = new ArrayList<>();
        listAllRepeat(list, length, "", resultList);
        return resultList;
    }

    public static void listAllRepeat(List<Integer> list, int length, String value, List<String> resultList) {
        if (value.split(SPLIT).length < length) {
            for (int i = 0; i < list.size(); i++) {
                String newValue = strAdd(value, String.valueOf(list.get(i)));
                listAllRepeat(list, length, newValue, resultList);
            }
        } else {
            resultList.add(value);
        }
    }

    public static String strAdd(String org, String add) {
        if (org == null || org.length() == 0) {
            return add;
        } else {
            return org + SPLIT + add;
        }
    }

    /**
     * 计算结果是否符合要求，符合则输出计算语句，否则返回null
     * @param strNumbers
     * @param opp
     * @return
     */
    public static String make(List<String> strNumbers, List<String> opp) {
        BigDecimal result = new BigDecimal(KEY_VALUE.get(strNumbers.get(0)));
        String resultStr = strNumbers.get(0);
        for (int i = 1; i < strNumbers.size() && i - 1 < opp.size(); i++) {
            resultStr += opp.get(i - 1) + strNumbers.get(i);
            BigDecimal number = new BigDecimal(KEY_VALUE.get(strNumbers.get(i)));

            switch (opp.get(i - 1)) {
                case "+":
                    result = result.add(number);
                    break;
                case "-":
                    result = result.subtract(number);
                    break;
                case "*":
                    result = result.multiply(number);
                    break;
                case "/":
                    result = result.divide(number, 9, BigDecimal.ROUND_HALF_UP);
                    //System.out.println("除法运行结果:" + result.toString());
                    break;
            }
        }

        BigDecimal resultScale = result.setScale(4, BigDecimal.ROUND_HALF_UP);
        //System.out.println("对比值:" + resultScale.toString());
        if (resultScale.compareTo(new BigDecimal(TARGET_NUMBER)) == 0) {
            return resultStr;
        }
        return null;
    }

    /*public static String make2(List<String> strNumbers, List<String> opp) {
        int result = KEY_VALUE.get(strNumbers.get(0));
        String resultStr = strNumbers.get(0);
        for (int i = 1; i < strNumbers.size() && i - 1 < opp.size(); i++) {
            resultStr += opp.get(i - 1) + strNumbers.get(i);
            int number = KEY_VALUE.get(strNumbers.get(i));

            switch (opp.get(i - 1)) {
                case "+":
                    result = result + number;
                    break;
                case "-":
                    result = result - number;
                    break;
                case "*":
                    result = result * number;
                    break;
                case "/":
                    result = result / number;
                    break;
            }
        }

        if (result == TARGET_NUMBER) {
            return resultStr;
        }
        return null;
    }*/
}
