package com.example.demo.util;

/**
 * Created by realcolorred on 2019/3/12.
 */
public class NumberHelper {

    public static boolean isVaildLong(Long num) {
        return num != null && num > 0;
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
}
