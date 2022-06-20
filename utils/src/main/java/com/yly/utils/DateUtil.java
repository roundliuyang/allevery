package com.yly.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Mengyuan Xing
 * @Date 2020/11/24
 */
public class DateUtil {

    public static final DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyyMMdd");
    public static final DateTimeFormatter HHmmss = DateTimeFormat.forPattern("HHmmss");
    public static final DateTimeFormatter yyy_MM_dd = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter yyyy_MM_ddHHmmss = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yyyyMMddHHmmssSSS = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 将yyyyMMddHHmmss转换成yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return
     */
    public static String dateConvertion(String str) {
        Date parse = null;
        String dateString = "";
        try {
            parse = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
            dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse);
        } catch (ParseException e) {
            dateString = null;
        }
        return dateString;
    }

    public static Date parse(String str, DateTimeFormatter dateTimeFormatter) {
        return DateTime.parse(str, dateTimeFormatter).toDate();
    }

    public static String toString(Date date, DateTimeFormatter dateTimeFormatter) {
        return toString(new DateTime(date), dateTimeFormatter);
    }

    public static String toString(DateTime date, DateTimeFormatter dateTimeFormatter) {
        return new DateTime(date).toString(dateTimeFormatter);
    }

    /**
     * 计算 day 天后的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date date = addDay(new Date(), -1);
        System.out.println(date);
    }


    public static List<Date> findDates(Date dBegin, Date dEnd)
    {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

}
