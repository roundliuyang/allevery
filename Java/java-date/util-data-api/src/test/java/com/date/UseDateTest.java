package com.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标准库API
 * 我们再来看一下Java标准库提供的API。Java标准库有两套处理日期和时间的API：
 * 一套定义在java.util这个包里面，主要包括Date、Calendar和TimeZone这几个类；
 * 一套新的API是在Java 8引入的，定义在java.time这个包里面，主要包括LocalDateTime、ZonedDateTime、ZoneId等。
 * 为什么会有新旧两套API呢？因为历史遗留原因，旧的API存在很多问题，所以引入了新的API。
 * 那么我们能不能跳过旧的API直接用新的API呢？如果涉及到遗留代码就不行，因为很多遗留代码仍然使用旧的API，所以目前仍然需要对旧的API有一定了解，很多时候还需要在新旧两种对象之间进行转换。
 * 本节我们快速讲解旧API的常用类型和方法。
 */


//Date
//java.util.Date是用于表示一个日期和时间的对象，注意与java.sql.Date区分，后者用在数据库中。
// 如果观察Date的源码，可以发现它实际上存储了一个long类型的以毫秒表示的时间戳：

public class UseDateTest {

    @Test
    public  void dateTest() {
        // 获取当前时间:
        Date date = new Date();
        System.out.println(date.getYear() + 1900); // 必须加上1900

        System.out.println(date.getMonth() + 1); // 0~11，必须加上1

        System.out.println(date.getDate()); // 1~31，不能加1

        // 转换为String:
        System.out.println(date.toString());
        // 转换为GMT时区:
        System.out.println(date.toGMTString());
        // 转换为本地时区:
        System.out.println(date.toLocaleString());
    }

    //打印本地时区表示的日期和时间时，不同的计算机可能会有不同的结果。如果我们想要针对用户的偏好精确地控制日期和时间的格式，就可以使用SimpleDateFormat对一个Date进行转换。它用预定义的字符串表示格式化：
    //yyyy：年
    //MM：月
    //dd: 日
    //HH: 小时
    //mm: 分钟
    //ss: 秒
    //我们来看如何以自定义的格式输出：
    @Test
     public void dateFormatTest(){
        // 获取当前时间:
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sd.format(date));


        //Java的格式化预定义了许多不同的格式，我们以MMM和E为例：
        // 获取当前时间:
        Date date2 = new Date();
        SimpleDateFormat sd2 = new SimpleDateFormat("E MMM dd, yyyy");
        System.out.println(sd2.format(date));
     }

     //上述代码在不同的语言环境会打印出类似Sun Sep 15, 2019这样的日期。可以从JDK文档查看详细的格式说明。一般来说，字母越长，输出越长。以M为例，假设当前月份是9月：
    //M：输出9
    //MM：输出09
    //MMM：输出Sep
    //MMMM：输出September
    //Date对象有几个严重的问题：它不能转换时区，除了toGMTString()可以按GMT+0:00输出外，
    // Date总是以当前计算机系统的默认时区为基础进行输出。此外，我们也很难对日期和时间进行加减，计算两个日期相差多少天，计算某个月第一个星期一的日期等。
}
