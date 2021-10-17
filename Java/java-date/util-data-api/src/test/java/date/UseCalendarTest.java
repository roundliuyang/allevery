package date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *Calendar可以用于获取并设置年、月、日、时、分、秒，它和Date比，主要多了一个可以做简单的日期和时间运算的功能
 */

//我们来看Calendar的基本用法：
public class UseCalendarTest {

    @Test
    public  void calendarTest() {
        // 获取当前时间:
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = 1 + c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int w = c.get(Calendar.DAY_OF_WEEK);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        System.out.println(y + "-" + m + "-" + d + " " + w + " " + hh + ":" + mm + ":" + ss + "." + ms);



        //注意到Calendar获取年月日这些信息变成了get(int field)，返回的年份不必转换，返回的月份仍然要加1，返回的星期要特别注意，1~7分别表示周日，周一，……，周六。
        //Calendar只有一种方式获取，即Calendar.getInstance()，而且一获取到就是当前时间。如果我们想给它设置成特定的一个日期和时间，就必须先清除所有字段：

        // 当前时间:
        Calendar cc = Calendar.getInstance();
        // 清除所有:
        cc.clear();
        // 设置2019年:
        cc.set(Calendar.YEAR, 2019);
        // 设置9月:注意8表示9月:
        cc.set(Calendar.MONTH, 8);
        // 设置2日:
        cc.set(Calendar.DATE, 2);
        // 设置时间:
        cc.set(Calendar.HOUR_OF_DAY, 21);
        cc.set(Calendar.MINUTE, 22);
        cc.set(Calendar.SECOND, 23);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cc.getTime()));
        // 2019-09-02 21:22:23
        //利用Calendar.getTime()可以将一个Calendar对象转换成Date对象，然后就可以用SimpleDateFormat进行格式化了。
    }


}
