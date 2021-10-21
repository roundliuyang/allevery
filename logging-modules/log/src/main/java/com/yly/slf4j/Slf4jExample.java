package com.yly.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To switch between logging frameworks you need only to uncomment needed framework dependencies in pom.xml
 */
public class Slf4jExample {
    private static Logger logger = LoggerFactory.getLogger(Slf4jExample.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        String variable = "Hello John";
        logger.debug("Printing variable value {} ", variable);
    }
}

/**
 * SLF4J 优点
 *
 * SLF4J 提供了额外的功能，可以提高日志记录的效率和代码的可读性。例如，SLF4J 为处理参数提供了一个非常有用的接口：
 * String variable = "Hello John";
 * logger.debug("Printing variable value: {}", variable);
 *
 * 这是做同样事情的 Log4j 的代码示例：
 *String variable = "Hello John";
 * logger.debug("Printing variable value: " + variable);
 *
 * 如您所见，无论是否启用调试级别，Log4j 都会连接字符串。在高负载应用程序中，这可能会导致性能问题。 SLF4J 将连接字符串
 * String variable = "Hello John";
 * if (logger.isDebugEnabled()) {
 *     logger.debug("Printing variable value: " + variable);
 * }
 *
 */




