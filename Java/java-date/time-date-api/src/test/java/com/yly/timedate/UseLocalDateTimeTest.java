package com.yly.timedate;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class UseLocalDateTimeTest {
    //从Java 8开始，java.time包提供了新的日期和时间API，主要涉及的类型有：
    //本地日期和时间：LocalDateTime，LocalDate，LocalTime；
    //带时区的日期和时间：ZonedDateTime；
    //时刻：Instant；
    //时区：ZoneId，ZoneOffset；
    //时间间隔：Duration。
    //以及一套新的用于取代SimpleDateFormat的格式化类型DateTimeFormatter。
    //和旧的API相比，新API严格区分了时刻、本地日期、本地时间和带时区的日期时间，并且，对日期和时间进行运算更加方便。
    //此外，新API修正了旧API不合理的常量设计：
    //Month的范围用1~12表示1月到12月；
    //Week的范围用1~7表示周一到周日。
    //最后，新API的类型几乎全部是不变类型（和String类似），可以放心使用不必担心被修改。

    //LocalDateTime
    //我们首先来看最常用的LocalDateTime，它表示一个本地日期和时间：
    @Test
    public void localDateTimeTest(){
        LocalDate d = LocalDate.now(); // 当前日期
        LocalTime t = LocalTime.now(); // 当前时间
        LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
        System.out.println(d); // 严格按照ISO 8601格式打印
        System.out.println(t); // 严格按照ISO 8601格式打印
        System.out.println(dt); // 严格按照ISO 8601格式打印


        //本地日期和时间通过now()获取到的总是以当前默认时区返回的，和旧API不同，LocalDateTime、LocalDate和LocalTime默认严格按照ISO 8601规定的日期和时间格式进行打印。

        //上述代码其实有一个小问题，在获取3个类型的时候，由于执行一行代码总会消耗一点时间，因此，
        //3个类型的日期和时间很可能对不上（时间的毫秒数基本上不同）。为了保证获取到同一时刻的日期和时间，可以改写如下：
        LocalDateTime dt2 = LocalDateTime.now(); // 当前日期和时间
        LocalDate d2 = dt.toLocalDate(); // 转换到当前日期
        LocalTime t2 = dt.toLocalTime(); // 转换到当前时间


        //反过来，通过指定的日期和时间创建LocalDateTime可以通过of()方法：
        // 指定日期和时间:
        LocalDate d3 = LocalDate.of(2019, 11, 30); // 2019-11-30, 注意11=11月
        LocalTime t3 = LocalTime.of(15, 16, 17); // 15:16:17
        LocalDateTime dt3 = LocalDateTime.of(2019, 11, 30, 15, 16, 17);
        LocalDateTime dt4 = LocalDateTime.of(d2, t2);

    }

    @Test
    public void localDateTimeTest2(){
        //因为严格按照ISO 8601的格式，因此，将字符串转换为LocalDateTime就可以传入标准格式：
        LocalDateTime dt = LocalDateTime.parse("2019-11-19T15:16:17");
        LocalDate d = LocalDate.parse("2019-11-19");
        LocalTime t = LocalTime.parse("15:16:17");

        //注意ISO 8601规定的日期和时间分隔符是T。标准格式如下：
        //日期：yyyy-MM-dd
        //时间：HH:mm:ss
        //带毫秒的时间：HH:mm:ss.SSS
        //日期和时间：yyyy-MM-dd'T'HH:mm:ss
        //带毫秒的日期和时间：yyyy-MM-dd'T'HH:mm:ss.SSS
    }


    //DateTimeFormatter
    //如果要自定义输出的格式，或者要把一个非ISO 8601格式的字符串解析成LocalDateTime，可以使用新的DateTimeFormatter：
    @Test
    public void useDateTimeFormatterTest(){
        // 自定义格式化:
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));

        // 用自定义格式解析:
        LocalDateTime dt2 = LocalDateTime.parse("2019/11/30 15:16:17", dtf);
        System.out.println(dt2);

    }


    //LocalDateTime提供了对日期和时间进行加减的非常简单的链式调用：
    @Test
    public void testLocalDateTime2(){
        LocalDateTime dt = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
        System.out.println(dt);
        // 加5天减3小时:
        LocalDateTime dt2 = dt.plusDays(5).minusHours(3);
        System.out.println(dt2); // 2019-10-31T17:30:59
        // 减1月:
        LocalDateTime dt3 = dt2.minusMonths(1);
        System.out.println(dt3); // 2019-09-30T17:30:59

        //注意到月份加减会自动调整日期，例如从2019-10-31减去1个月得到的结果是2019-09-30，因为9月没有31日。
        //对日期和时间进行调整则使用withXxx()方法，例如：withHour(15)会把10:11:12变为15:11:12：
        //调整年：withYear()
        //调整月：withMonth()
        //调整日：withDayOfMonth()
        //调整时：withHour()
        //调整分：withMinute()
        //调整秒：withSecond()
        //示例代码如下：
        LocalDateTime ldt = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
        System.out.println(ldt);
        // 日期变为31日:
        LocalDateTime ldt2 = ldt.withDayOfMonth(31);
        System.out.println(ldt2); // 2019-10-31T20:30:59
        // 月份变为9:
        LocalDateTime ldt3 = ldt2.withMonth(9);
        System.out.println(ldt3); // 2019-09-30T20:30:59

        //同样注意到调整月份时，会相应地调整日期，即把2019-10-31的月份调整为9时，日期也自动变为30。

    }


    //实际上，LocalDateTime还有一个通用的with()方法允许我们做更复杂的运算。例如：
    @Test
    public void  withTest(){
        // 本月第一天0:00时刻:
        LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        System.out.println(firstDay);

        // 本月最后1天:
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);

        // 下月第1天:
        LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(nextMonthFirstDay);

        // 本月第1个周一:
        LocalDate firstWeekday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstWeekday);

        //对于计算某个月第1个周日这样的问题，新的API可以轻松完成。
        //要判断两个LocalDateTime的先后，可以使用isBefore()、isAfter()方法，对于LocalDate和LocalTime类似：

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        System.out.println(now.isBefore(target));
        System.out.println(LocalDate.now().isBefore(LocalDate.of(2019, 11, 19)));
        System.out.println(LocalTime.now().isAfter(LocalTime.parse("08:15:00")));

        //注意到LocalDateTime无法与时间戳进行转换，因为LocalDateTime没有时区，无法确定某一时刻。后面我们要介绍的ZonedDateTime相当于LocalDateTime加时区的组合，它具有时区，可以与long表示的时间戳进行转换。
    }

    // Duration和Period
    @Test
    public void test3() {
        //Duration表示两个时刻之间的时间间隔。另一个类似的Period表示两个日期之间的天数：

        LocalDateTime start = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 9, 19, 25, 30);
        Duration d = Duration.between(start, end);
        System.out.println(d); // PT1235H10M30S

        Period p = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
        System.out.println(p); // P1M21D

        //注意到两个LocalDateTime之间的差值使用Duration表示，类似PT1235H10M30S，表示1235小时10分钟30秒。而两个LocalDate之间的差值用Period表示，类似P1M21D，表示1个月21天。
        //Duration和Period的表示方法也符合ISO 8601的格式，它以P...T...的形式表示，P...T之间表示日期间隔，T后面表示时间间隔。如果是PT...的格式表示仅有时间间隔。利用ofXxx()或者parse()方法也可以直接创建Duration：
        //Duration d1 = Duration.ofHours(10); // 10 hours
        //Duration d2 = Duration.parse("P1DT2H3M"); // 1 day, 2 hours, 3 minutes
    }

    //小结
    //Java 8引入了新的日期和时间API，它们是不变类，默认按ISO 8601标准格式化和解析；
    //使用LocalDateTime可以非常方便地对日期和时间进行加减，或者调整日期和时间，它总是返回新对象；
    //使用isBefore()和isAfter()可以判断日期和时间的先后；
    //使用Duration和Period可以表示两个日期和时间的“区间间隔”。
}
