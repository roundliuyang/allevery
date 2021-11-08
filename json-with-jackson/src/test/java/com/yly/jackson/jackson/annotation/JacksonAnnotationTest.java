package com.yly.jackson.jackson.annotation;

/**
 * 在本文中，我们尝试了解如何在 Jackson 中使用@JsonFormat。这是一个 Jackson 注释，用于指定如何格式化 JSON 输出的字段和/或属性。
 * 具体而言，此批注允许您指定如何根据SimpleDateFormat格式设置日期和日历值的格式。
 */
public class JacksonAnnotationTest {

    /*
        使用默认格式
        首先，我们将演示将@JsonFormat注释与代表用户的类一起使用的概念。
        于我们试图解释注释的细节，因此User对象将根据请求创建（而不是从数据库存储或加载）并序列化为 JSON：
        public class User {
            private String firstName;
            private String lastName;
            private Date createdDate = new Date();

            // standard constructor, setters and getters
        }
        构建并运行此代码示例将返回以下输出：
        {"firstName":"John","lastName":"Smith","createdDate":1482047026009}
        如您所见，createdDate字段显示为自纪元以来的秒数，这是日期字段使用的默认格式。
    */

//    -------------------------------------------------------------------------------------------------------------------

    /*
        在 Getter 上使用注解
        现在让我们使用@JsonFormat来指定应序列化createdDate字段的格式。这是针对此更改更新的 User 类。该createdDate场已经被注释如图所示，以指定的日期格式。
        用于模式参数的数据格式由SimpleDateFormat指定：
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
        private Date createdDate;
        有了这个更改，我们再次构建项目并运行它。输出如下所示：
        {"firstName":"John","lastName":"Smith","createdDate":"2016-12-18@07:53:34.740+0000"}
        如您所见，createdDate字段已使用指定的SimpleDateFormat格式使用@JsonFormat注释进行格式化。

        上面的示例演示了在字段上使用注释。它也可以用于 getter 方法（一个属性），如下所示。
        例如，您可能有一个在调用时计算的属性。在这种情况下，您可以在 getter 方法上使用注释。请注意，该模式也已更改为仅返回即时的日期部分：

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
         public Date getCurrentDate() {
             return new Date();
         }
        结果输出如下：
        { ... , "currentDate":"2016-12-18", ...}
     */


//    -------------------------------------------------------------------------------------------------------
    /*
        指定语言环境
        除了指定日期格式，您还可以指定用于序列化的区域设置。不指定此参数会导致使用默认语言环境执行序列化：
        @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
            public Date getCurrentDate() {
            return new Date();
        }
     */


//    ---------------------------------------------------------------------------------------------------------------


    /*
        Specifying the Shape
        使用@JsonFormat，并将shape设置为JsonFormat.Shape.NUMBER，会导致Date类型的默认输出--作为自纪元以来的秒数。
        参数模式不适用于这种情况并被忽略
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        public Date getDateNum() {
            return new Date();
        }
        输出如下所示：
        { ..., "dateNum":1482054723876 }
     */

    // 结论,总之，@JsonFormat用于控制Date和Calendar类型的输出格式，如上所示。

}

