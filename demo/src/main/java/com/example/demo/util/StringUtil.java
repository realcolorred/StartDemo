package com.example.demo.util;

public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
