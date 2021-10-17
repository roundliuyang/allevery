package com.yly.timedate;

public class UseLocalDateTimeTest {
    //从Java 8开始，java.time包提供了新的日期和时间API，主要涉及的类型有：
    //
    //本地日期和时间：LocalDateTime，LocalDate，LocalTime；
    //带时区的日期和时间：ZonedDateTime；
    //时刻：Instant；
    //时区：ZoneId，ZoneOffset；
    //时间间隔：Duration。
    //以及一套新的用于取代SimpleDateFormat的格式化类型DateTimeFormatter。
    //
    //和旧的API相比，新API严格区分了时刻、本地日期、本地时间和带时区的日期时间，并且，对日期和时间进行运算更加方便。
    //
    //此外，新API修正了旧API不合理的常量设计：
    //
    //Month的范围用1~12表示1月到12月；
    //Week的范围用1~7表示周一到周日。
    //最后，新API的类型几乎全部是不变类型（和String类似），可以放心使用不必担心被修改。
}
