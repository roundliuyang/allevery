package com.yly.allevery.java.string;

/**
 * 空与空白
 * 当然，知道字符串何时为空或空白是很常见的，但让我们确保我们与定义在同一页面上。
 * 如果一个字符串为空或一个没有任何长度的字符串，我们就认为它是空的。如果一个字符串只包含空格，那么我们称它为空白。
 * 对于 Java，空格是空格、制表符等字符。看看 Character.isWhitespace 的例子。
 */
public class CheckEmptyOrBlank {

    //空字符串( Empty Strings)

    //使用 Java 6 及更高版本
    //如果我们至少在 Java 6 上，那么检查空字符串的最简单方法是String#isEmpty：
    boolean isEmptyString(String string) {
        return string.isEmpty();
    }

    //为了使它也是空安全的，我们需要添加一个额外的检查：
    boolean isEmptyString2(String string) {
        return string == null || string.isEmpty();
    }

//    ----------------------------------------------------------------------

    //空白字符串（Blank Strings）
    //如果我们还想检测空白字符串，我们可以在String#trim的帮助下实现这一点。它将在执行检查之前删除所有前导和尾部的空白。
    boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
    //还要记住String是不可变的，所以调用 trim 不会真正改变底层字符串。


    //----------------------------------------------------------------------------------------
    //Bean 验证
    //另一种检查空字符串的方法是正则表达式。这在Java Bean Validation 中很方便：
    //  @Pattern(regexp = "\\A(?!\\s*\\Z).+")
    //  String someString;
    //给定的正则表达式确保空字符串或空白字符串不会验证。



    //--------------------------------------------------------------------------------
    //使用 Apache Commons
    //如果可以添加依赖，我们可以使用Apache Commons Lang。这有许多 Java 助手
    //If we use Maven, we need to add the commons-lang3 dependency to our pom:

    //  <dependency>
    //     <groupId>org.apache.commons</groupId>
    //      <artifactId>commons-lang3</artifactId>
    //  </dependency>

    //除此之外，这给了我们StringUtils。
    //此类带有诸如isEmpty、isBlank等方法：
    //StringUtils.isBlank(string)
    //此调用与我们自己的isBlankString方法的作用相同。它是空安全的并且还会检查空格(It's null-safe and also checks for whitespaces.)。


    //---------------------------------------------------------------------------------------------------------

    //Guava
    //另一个提供某些字符串相关实用程序的著名库是 Google 的 Guava。从 23.1 版开始，有两种 Guava 版本：android 和 jre。 Android 版本针对 Android 和 Java 7，而 JRE 版本针对 Java 8。
    //如果我们不针对 Android，我们可以将 JRE 风格添加到我们的 pom 中：

    //   <dependency>
    //       <groupId>com.google.guava</groupId>
    //       <artifactId>guava</artifactId>
    //       <version>28.0-jre</version>
    //   </dependency>
    //  Guavas Strings 类带有一个方法 Strings.isNullOrEmpty：
    //它检查给定的字符串是 null 还是空的，但不会检查只有空格的字符串

}
