package com.yly.commons.lang3.StringUtils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class StringUtilsTest {


    //Maven Dependency
    //  <dependency>
    //      <groupId>org.apache.commons</groupId>
    //      <artifactId>commons-lang3</artifactId>
    //      <version>3.12.0</version>
    //  </dependency>

    // StringUtils
    //StringUtils 类提供了对字符串进行空安全操作的方法。


    //---------------------------------------------------------------------------------------

    // containsAny方法
    //containsAny 方法检查给定的字符串是否包含给定字符集中的任何字符。这组字符可以以字符串或字符可变参数的形式传递
    //以下代码片段演示了此方法的两种重载风格与结果验证的使用：
    @Test
    public void testStringUtils(){
        String string = "baeldung.com";
        boolean contained1 = StringUtils.containsAny(string, 'a', 'b', 'c');
        boolean contained2 = StringUtils.containsAny(string, 'x', 'y', 'z');
        boolean contained3 = StringUtils.containsAny(string, "abc");
        boolean contained4 = StringUtils.containsAny(string, "xyz");

        assertTrue(contained1);
        assertFalse(contained2);
        assertTrue(contained3);
        assertFalse(contained4);
    }


    //-----------------------------------------------------------------------------------------------------------

    //containsIgnoreCase方法
    //该containsIgnoreCase方法检查是否一个给定的字符串包含另一个字符串在不区分大小写的方式。
    //以下代码片段验证字符串“baeldung.com”在忽略大小写时是否包含“BAELDUNG”：
    @Test
    public void  testStringUtils2() {
        String string = "baeldung.com";
        boolean contained = StringUtils.containsIgnoreCase(string, "BAELDUNG");

        assertTrue(contained);
    }

    //------------------------------------------------------------------------------------------

    // countMatches方法
    //counterMatches 方法计算字符或子字符串在给定字符串中出现的次数。
    //以下是该方法的演示，确认在字符串“welcome to www.baeldung.com”中，‘w’出现了四次，而“com”出现了两次：
    @Test
    public void testStringUtils3(){
        String string = "welcome to www.baeldung.com";
        int charNum = StringUtils.countMatches(string, 'w');
        int stringNum = StringUtils.countMatches(string, "com");

        assertEquals(4, charNum);
        assertEquals(2, stringNum);
    }


    //---------------------------------------------------------------------------------------------

    //Appending and Prepending方法
    //appendIfMissing和appendIfMissingIgnoreCase方法分别以区分大小写和不区分大小写的方式将一个后缀添加到给定的字符串的结尾，如果它还没有以任何传入的后缀结束。
    //同样，如果一个给定的字符串没有以任何传入的前缀开始，pependIfMissing和pependIfMissingIgnoreCase方法将前缀加到字符串的开头。
    //在下面的例子中，appendIfMissing和pependIfMissing方法被用来给字符串 "baeldung.com "添加后缀和前缀，而这些后缀不会被重复。
    @Test
    public void testStringUtils4(){
        String string = "baeldung.com";
        String stringWithSuffix = StringUtils.appendIfMissing(string, ".com");
        String stringWithPrefix = StringUtils.prependIfMissing(string, "www.");

        assertEquals("baeldung.com", stringWithSuffix);
        assertEquals("www.baeldung.com", stringWithPrefix);

    }

    //---------------------------------------------------------------------------------------------

    // Case Changing 方法
    //String类已经定义了将String的所有字符转换为大写或小写的方法。本小节只说明了以其他方式改变字符串大小写的方法的使用，包括swapCase、capitalize和uncapitalize。

    //swapCase方法交换字符串的大小写，将大写变成小写，将小写变成大写。
    @Test
    public void testStringUtils5(){
        String originalString = "baeldung.COM";
        String swappedString = StringUtils.swapCase(originalString);
        assertEquals("BAELDUNG.com", swappedString);

        //大写方法将一个给定的字符串的第一个字符转换为大写字母，其余的字符不做改变。
        String originalString2 = "baeldung";
        String capitalizedString = StringUtils.capitalize(originalString2);
        assertEquals("Baeldung", capitalizedString);

        //uncapitalize 方法将给定 String 的第一个字符转换为小写，保留所有剩余字符不变：
        String originalString3 = "Baeldung";
        String uncapitalizedString = StringUtils.uncapitalize(originalString3);
        assertEquals("baeldung", uncapitalizedString);
    }

    //---------------------------------------------------------------------------------------------

    //Reversing Method
    //StringUtils类定义了两个用于反转字符串的方法：reverse和reverseDelimited。reverse方法以相反的顺序重新排列一个字符串的所有字符，而reverseDelimited方法重新排列字符组，用指定的分隔符分隔。
    @Test
    public void testStringUtils6(){
        //以下代码片段反转字符串“baeldung”并验证结果：
        String originalString = "baeldung";
        String reversedString = StringUtils.reverse(originalString);
        assertEquals("gnudleab", reversedString);

        //使用reverseDelimited方法，字符被成组反转，而不是单独反转
        String originalString2 = "www.baeldung.com";
        String reversedString2 = StringUtils.reverseDelimited(originalString2, '.');

        assertEquals("com.baeldung.www", reversedString2);

    }

    //---------------------------------------------------------------------------------------------

    //旋转方法
    //rotate()方法将一个字符串的字符循环移动若干位置。下面的代码片段将字符串 "baeldung "的所有字符向右移动了四个位置，并验证了这个结果。
    @Test
    public void testStringUtils7(){
        String originalString = "baeldung";
        String rotatedString = StringUtils.rotate(originalString, 4);

        assertEquals("dungbael", rotatedString);
    }

    //---------------------------------------------------------------------------------------------

    // difference Method
    //difference方法比较两个字符串，返回第二个字符串的剩余部分，从它与第一个字符串不同的位置开始。下面的代码片段比较了两个字符串。"Baeldung Tutorials "和 "Baeldung Courses "在两个方向上进行比较，并验证结果。
    public void testStringUtils8(){
        String tutorials = "Baeldung Tutorials";
        String courses = "Baeldung Courses";
        String diff1 = StringUtils.difference(tutorials, courses);
        String diff2 = StringUtils.difference(courses, tutorials);

        assertEquals("Courses", diff1);
        assertEquals("Tutorials", diff2);
    }








}
