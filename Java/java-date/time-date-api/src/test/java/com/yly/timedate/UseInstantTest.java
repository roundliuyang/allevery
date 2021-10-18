package com.yly.timedate;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UseInstantTest {

    @Test
    public  void instantTest(){
        Instant now = Instant.now();
        System.out.println(now.getEpochSecond()); // 秒
        System.out.println(now.toEpochMilli()); // 毫秒

        //实际上，Instant内部只有两个核心字段：
        //    public final class Instant implements ... {
        //        private final long seconds;
        //        private final int nanos;
        //    }
        //一个是以秒为单位的时间戳，一个是更精确的纳秒精度。它和System.currentTimeMillis()返回的long相比，只是多了更高精度的纳秒。
    }

    //既然Instant就是时间戳，那么，给它附加上一个时区，就可以创建出ZonedDateTime：
    @Test
    public void  instantTest2(){
        // 以指定时间戳创建Instant:
        Instant ins = Instant.ofEpochSecond(1568568760);
        ZonedDateTime zdt = ins.atZone(ZoneId.systemDefault());
        System.out.println(zdt); // 2019-09-16T01:32:40+08:00[Asia/Shanghai]

        //可见，对于某一个时间戳，给它关联上指定的ZoneId，就得到了ZonedDateTime，继而可以获得了对应时区的LocalDateTime。
        //所以，LocalDateTime，ZoneId，Instant，ZonedDateTime和long都可以互相转换：
        //转换的时候，只需要留意long类型以毫秒还是秒为单位即可。
    }
    //Instant表示高精度时间戳，它可以和ZonedDateTime以及long互相转换。
}
