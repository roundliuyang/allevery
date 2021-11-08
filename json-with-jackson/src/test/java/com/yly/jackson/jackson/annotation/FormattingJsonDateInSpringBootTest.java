package com.yly.jackson.jackson.annotation;

public class FormattingJsonDateInSpringBootTest {

   /*
    1. 概述
    在本教程中，我们将展示如何在 Spring Boot 应用程序中格式化 JSON 日期字段。
    我们将探索使用Jackson格式化日期的各种方法，Spring Boot 将其用作其默认的 JSON 处理器。

    2.在日期字段上使用@JsonFormat
    2.1. 设置格式
    我们可以使用 @JsonFormat注释来格式化特定字段：
    public class Contact {
        // other fields
        @JsonFormat(pattern="yyyy-MM-dd")
        private LocalDate birthday;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime lastUpdate;

        // standard getters and setters

    }
    在birthday字段中，我们使用一种仅呈现日期的模式，而在 lastUpdate字段中，我们还包括时间。
    我们使用了Java 8 日期类型，它对于处理时间类型非常方便。当然，如果我们需要使用java.util.Date 这样的遗留类型 ，同样可以使用注解：
    public class ContactWithJavaUtilDate {
        // other fields
        @JsonFormat(pattern="yyyy-MM-dd")
        private Date birthday;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private Date lastUpdate;

        // standard getters and setters
    }
    最后，让我们看一下使用具有给定日期格式的@JsonFormat 呈现的输出 ：
    {
        "birthday": "2019-02-03",
            "lastUpdate": "2019-02-03 10:08:02"
    }
    正如我们所见，使用@JsonFormat 注释是格式化特定日期字段的绝佳方式。

    但是，我们应该只在需要为字段设置特定格式时才使用它。如果我们希望应用程序中的所有日期都有一个通用格式，那么有更好的方法来实现这一点，我们将在后面看到。
     2.2. 设置时区
    另外，如果我们需要使用特定的时区，我们可以设置@JsonFormat的timezone属性：
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime lastUpdate;
    如果类型已经包含时区，我们不需要使用它，例如 java.time.ZonedDatetime。

     3. 配置默认格式
    虽然@JsonFormat本身就很强大，但硬编码格式和时区可能会让我们陷入困境。
    如果我们想为应用程序中的所有日期配置默认格式，更灵活的方法是在application.properties 中进行配置：
    spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
    如果我们想在 JSON 日期中使用特定的时区，还有一个属性：
    spring.jackson.time-zone=Europe/Zagreb
    尽管像这样设置默认格式非常方便和直接，但这种方法有一个缺点。不幸的是，它不适用于 Java 8 日期类型，如 LocalDate 和 LocalDateTime—— 我们只能使用它来格式化java.util.Date或 java.util.Calendar类型的字段 。 不过，我们很快就会看到希望。

    4.自定义Jackson的ObjectMapper
    因此，如果我们想使用 Java 8 日期类型 并 设置默认日期格式，那么我们需要查看创建 Jackson2ObjectMapperBuilderCustomizer bean：
    @Configuration
    public class ContactAppConfig {

        private static final String dateFormat = "yyyy-MM-dd";
        private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
            return builder -> {
                builder.simpleDateFormat(dateTimeFormat);
                builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
                builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            };
        }

    }
    上面的示例显示了如何在我们的应用程序中配置默认格式。我们必须定义一个 bean 并覆盖它的 自定义 方法来设置所需的格式。
    虽然这种方法可能看起来有点麻烦，但它的好处是它适用于 Java 8 和遗留日期类型。

     5. 结论
    在本文中，我们探讨了在 Spring Boot 应用程序中格式化 JSON 日期的多种不同方法。
    */
}
