package com.example.common.util;

public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str) || "".equals(str.trim());
    }

    public static boolean isNotBlack(String str) {
        return !isBlank(str);
    }
}
