package com.example.live.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String getTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        return sdf.format(date);
    }

    // 和当前时间比较
    public static boolean comTsVal(String ts, int val) {
        long nm = 1000 * 60;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            Date date = sdf.parse(ts);
            long com = date.getTime();
            long cur = System.currentTimeMillis();
            return cur - com > val*nm;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径
     */
    public static String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy年MM月dd日");
    }


    // 2个时间差几天
    public static long daysOfTime_2(String date_pre, String date_after) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        // 跨年不会出现问题
        // 如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
        try {
            Date fDate = sdf.parse(date_pre);
            Date oDate = sdf.parse(date_after);
            return (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
        } catch (Exception e) {
            return 0;
        }
    }

    // 当前时间加x天
    public static String getDateDay(String date, int addDay, String format) {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        try {
            calendar1.setTime(sdf1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar1.add(Calendar.DATE, addDay);
        return sdf1.format(calendar1.getTime());
    }

    public static long dateToStamp(String time, String tag) {
        long stamp =0;
        if (StringUtils.isBlank(time)) {
            return stamp;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(tag);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            stamp =date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stamp;
    }

}

