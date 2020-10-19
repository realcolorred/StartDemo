package com.example.common.util;

import java.math.BigDecimal;

/**
 * Created by realcolorred on 2019/3/12.
 */
public class NumberUtil {

    public static boolean isValidNum(Long num) {
        return num != null && num > 0;
    }

    public static boolean isValidNum(Integer num) {
        return num != null && num > 0;
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static Integer toInt(double number) {
        // double转化为int，小数位四舍五入
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public static long toLong(String str) {
        return toLong(str, 0);
    }

    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

}
