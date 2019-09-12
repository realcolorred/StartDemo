package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String YYYYMMDDHHMMSS      = "yyyyMMddHHmmss";
    public static String YYYYMMDDHHMMSS_read = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentTime() {
        return toString(new Date(), YYYYMMDDHHMMSS);
    }

    public static String getCurrentTimeRead() {
        return toString(new Date(), YYYYMMDDHHMMSS_read);
    }

    public static String toString(Date date) {
        return toString(date, YYYYMMDDHHMMSS);
    }

    public static String toString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
