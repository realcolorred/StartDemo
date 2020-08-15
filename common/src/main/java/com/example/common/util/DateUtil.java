package com.example.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String YYYYMMDDHHMMSS      = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS   = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDDHHMMSS_READ = "yyyy-MM-dd HH:mm:ss";
    public static final Date   NULL                = new Date(0);

    public static String dateToString(Date date) {
        return dateToString(date, YYYYMMDDHHMMSS);
    }

    public static String dateToStringRead(Date date) {
        return dateToString(date, YYYYMMDDHHMMSS_READ);
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date strToDate(String str) {
        return strToDate(str, YYYYMMDDHHMMSS);
    }

    public static Date strToDate(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            return NULL;
        }
    }

    /**
     * @param timeStr 格式 :(-)yyyy/MM/dd
     * @return
     */
    public static long getTimes(String timeStr) {
        if (StringUtil.isEmpty(timeStr)) {
            return 0;
        }
        Calendar ca = Calendar.getInstance();
        ca.set(1800, Calendar.JANUARY, 1, 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        String[] timeList = timeStr.split("/");
        for (int i = 0; i < timeList.length; i++) {
            if (i == 0) {
                ca.set(Calendar.YEAR, NumberUtil.toInt(timeList[i]));
            }
            if (i == 1) {
                ca.set(Calendar.MONTH, NumberUtil.toInt(timeList[i]) - 1);
            }
            if (i == 2) {
                ca.set(Calendar.DATE, NumberUtil.toInt(timeList[i]));
            }

        }
        return ca.getTimeInMillis();
    }

    /**
     * @param startTime 开始时间 格式 :(-)yyyy/MM/dd
     * @param endTime   结束时间 格式 :(-)yyyy/MM/dd
     * @return 间隔年限
     */
    public static long getYearBetween(String startTime, String endTime) {
        int start = NumberUtil.toInt(startTime.split("/")[0]);
        int end = NumberUtil.toInt(endTime.split("/")[0]);
        int between = end - start;
        if (between < 0) {
            return 0 - between;
        }
        return between;
    }
}
