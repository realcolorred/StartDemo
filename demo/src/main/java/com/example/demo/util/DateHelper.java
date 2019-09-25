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

    /**
     *
     * @param timeStrRow 格式 :(-)yyyy/MM/dd
     * @return 从公元前以来的天数
     */
    public static int getDayOfTime(String timeStrRow) {
        int day = 0;
        boolean isSubtract = false;
        String timeStr = timeStrRow;

        if (timeStrRow.startsWith("-")) {
            isSubtract = true;
            timeStr = timeStrRow.replace("-", "");
        }

        String[] timeList = timeStr.split("/");
        for (int i = 0; i < timeList.length; i++) {
            if (i == 0) {
                day += NumberHelper.toInt(timeList[i]) * 365;
            }
            if (i == 1) {
                day += NumberHelper.toInt(timeList[i]) * 30;
            }
            if (i == 2) {
                day += NumberHelper.toInt(timeList[i]);
            }

        }
        if (isSubtract)
            return 0 - day;
        return day;
    }

    public static int getYearBetween(String startTime, String endTime) {
        int start = NumberHelper.toInt(startTime.split("/")[0]);
        int end = NumberHelper.toInt(endTime.split("/")[0]);
        int between = end - start;
        if (between < 0) {
            return 0 - between;
        }
        return between;
    }
}
