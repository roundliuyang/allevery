package com.yly.utildate;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *TimeZone
 * Calendar和Date相比，它提供了时区转换的功能。时区用TimeZone对象表示：
 */


public class UseTimeZoneTest {

    @Test
    public  void timeZoneTest() {
        TimeZone tzDefault = TimeZone.getDefault(); // 当前时区
        TimeZone tzGMT9 = TimeZone.getTimeZone("GMT+09:00"); // GMT+9:00时区
        TimeZone tzNY = TimeZone.getTimeZone("America/New_York"); // 纽约时区
        System.out.println(tzDefault.getID()); // Asia/Shanghai
        System.out.println(tzGMT9.getID()); // GMT+09:00
        System.out.println(tzNY.getID()); // America/New_York

        //时区的唯一标识是以字符串表示的ID，我们获取指定TimeZone对象也是以这个ID为参数获取，GMT+09:00、Asia/Shanghai都是有效的时区ID。要列出系统支持的所有ID，请使用TimeZone.getAvailableIDs()。


        // 当前时间:
        Calendar c = Calendar.getInstance();
        // 清除所有:
        c.clear();
        // 设置为北京时区:
        c.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 设置年月日时分秒:
        c.set(2019, 10 /* 11月 */, 20, 8, 15, 0);
        System.out.println(c.getTime());
        // 显示时间:
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sd.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println(sd.format(c.getTime()));
        // 2019-11-19 19:15:00

        //可见，利用Calendar进行时区转换的步骤是：
        //清除所有字段；
        //设定指定时区；
        //设定日期和时间；
        //创建SimpleDateFormat并设定目标时区；
        //格式化获取的Date对象（注意Date对象无时区信息，时区信息存储在SimpleDateFormat中）。
        //因此，本质上时区转换只能通过SimpleDateFormat在显示的时候完成。
    }

    //Calendar也可以对日期和时间进行简单的加减：
    @Test
    public void test2(){
        // 当前时间:
        Calendar c = Calendar.getInstance();
        // 清除所有:
        c.clear();
        // 设置年月日时分秒:
        c.set(2019, 10 /* 11月 */, 20, 8, 15, 0);
        // 加5天并减去2小时:
        c.add(Calendar.DAY_OF_MONTH, 5);
        c.add(Calendar.HOUR_OF_DAY, -2);
        // 显示时间:
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = c.getTime();
        System.out.println(sd.format(d));
        // 2019-11-25 6:15:00
    }

    //小结
    //计算机表示的时间是以整数表示的时间戳存储的，即Epoch Time，Java使用long型来表示以毫秒为单位的时间戳，通过System.currentTimeMillis()获取当前时间戳。
    //Java有两套日期和时间的API：
    //旧的Date、Calendar和TimeZone；
    //新的LocalDateTime、ZonedDateTime、ZoneId等。
    //分别位于java.util和java.time包中。
}
