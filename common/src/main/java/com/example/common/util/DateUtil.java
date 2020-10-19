package com.example.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String YYYYMMDD_READ          = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS         = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS      = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDDHHMMSS_READ    = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSSSSS_READ = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final Date   NULL                   = new Date(0);

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
     * 返回Date对象的年份
     *
     * @param date
     *            日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回Date对象的月份
     *
     * @param date
     *            日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回Date对象的日份
     *
     * @param date
     *            日
     * @return 返回日期
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回Date对象的小时
     *
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回Date对象的分钟
     *
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒
     *
     * @param date
     *            日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date
     *            日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期加月
     *
     * @param date
     *            日期
     * @param month
     *            加的月数
     * @return 返回相加后的日期
     */

    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 日期相加
     *
     * @param date
     *            日期
     * @param day
     *            加的天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期加小时
     *
     * @param date
     *            日期
     * @param hours
     *            小时数
     * @return 返回相加后的日期
     */
    public static Date addHours(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) hours) * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期加分钟
     *
     * @param date
     * @param minutes
     *            分钟
     * @return 返回相加后的日期
     */
    public static Date addMinutes(Date date, long minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minutes) * 60 * 1000);
        return c.getTime();
    }

    /**
     * 日期加分钟
     *
     * @param date
     * @param minutes
     *            分钟
     * @return 返回相加后的日期
     */
    public static Date add(Date date, long minutes) {
        if (date == null) {
            throw new IllegalArgumentException();
        }

        return addMinutes(date, minutes);
    }

    /**
     * @param minuend
     * @param subtrahend
     * @return 两个时间点相差的秒数(minuend - subtrahend)
     */
    public static long sub(Date minuend, Date subtrahend) {
        long subResult = 0;
        if (minuend == null || subtrahend == null) {
            throw new IllegalArgumentException();
        }
        subResult = minuend.getTime() - subtrahend.getTime();
        subResult = subResult / 1000;
        return subResult;
    }

    /**
     * @param beginDate
     * @param endDate
     * @return 两个时间点相差的天数(beginDate - endDate)
     */
    public static long between(Date beginDate, Date endDate) {
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(beginDate);
        calEnd.setTime(endDate);
        calBegin.clear(14);
        calEnd.clear(14);
        long millisecs = calBegin.getTime().getTime() - calEnd.getTime().getTime();
        long remainder = millisecs % 0x5265c00L;
        return (millisecs - remainder) / 0x5265c00L;
    }

    /**
     * 特殊时间格式：获取毫秒数
     * @param timeStr 格式 :(-)yyyy/MM/dd
     * @return 毫秒
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
     * 特殊时间格式：计算相隔年限
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
