package com.yly.jackson.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yly.jackson.objectmapper.dto.Car;
import com.yly.jackson.objectmapper.dto.CmsMediaVendorDto;
import com.yly.jackson.objectmapper.dto.Request;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Author: yly
 * Date: 2021/10/20 16:08
 * <p>
 * 让我们从基本的读写操作开始。
 * ObjectMapper的简单readValue API是一个很好的切入点。我们可以使用它将 JSON 内容解析或反序列化为 Java 对象。
 * 此外，在写入方面，我们可以使用writeValue API 将任何 Java 对象序列化为 JSON 输出。
 */
public class SerializationDeserializationFeatureUnitTest {


    // Java对象到jackson
    // 让我们看第一个使用ObjectMapper类的writeValue方法将 Java 对象序列化为 JSON 的示例：
    @Test
    public void objectMapperTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CmsMediaVendorDto cmsMediaVendorDto = new CmsMediaVendorDto();
//        Car car = new Car("yellow", "renault");
        File file = new File("target/car.json");
        objectMapper.writeValue(file, cmsMediaVendorDto);

        // 方法writeValueAsString和writeValueAsBytes的ObjectMapper类生成从Java对象的JSON，并返回所生成的JSON作为一个字符串或字节数组：
        String carAsString = objectMapper.writeValueAsString(cmsMediaVendorDto);
        System.out.println(carAsString);
    }

    // Json到Java对象
    // 下面是一个使用ObjectMapper类将Json字符串转换为Java对象的简单示例
    @Test
    public void objectMapperTest2() throws IOException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(json, Car.class);

        //所述readValue（）函数还接受其他形式的输入，诸如包含JSON字符串文件：
        Car car2 = objectMapper.readValue(new File("src/test/resources/json_car.json"), Car.class);
        //或网址
        Car car3 = objectMapper.readValue(new URL("file:src/test/resources/json_car.json"), Car.class);
    }


    // JSON到jackson JsonNode
    // 或者，可以将 JSON 解析为JsonNode对象并用于从特定节点检索数据：
    public void objectMapperTest3() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
        // Output: color -> Black
    }


    // 从 JSON 数组字符串创建 Java 列表
    // 我们可以使用 TypeReference 将数组形式的 JSON 解析为 Java 列表
    public void objectMapperTest4() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
    }


    // 从 JSON 字符串创建 Java Map
    public void objectMapperTest5() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map
                = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
    }



    // 高级功能
    // Jackson 库的最大优势之一是高度可定制的序列化合反序列化过程
    // 在本节中，我们将介绍一些高级功能，其中输入或输出 JSON 响应可能与生成或使用响应的对象不同。


    //配置序列化或反序列化功能
    //在将 JSON 对象转换为 Java 类时，如果 JSON 字符串有一些新字段，默认过程将导致异常
    @Test
    public void objectMapperTest6() throws JsonProcessingException {
        String jsonString
                = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
        // 上例中的 JSON 字符串在默认解析为类 Car的 Java 对象的过程中将导致UnrecognizedPropertyException异常。

        // 通过configure方法，我们可以扩展默认流程来忽略新字段：
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = objectMapper.readValue(jsonString, Car.class);

        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();

        //另一个选项基于 FAIL_ON_NULL_FOR_PRIMITIVES，它定义是否允许原始值的空值
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        //类似地，FAIL_ON_NUMBERS_FOR_ENUM 控制是否允许将枚举值序列化/反序列化为数字：
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

        //您可以在官方网站上找到序列化和反序列化功能的完整列表
    }


    // 创建自定义序列化器或反序列化器
    // ObjectMapper类的另一个基本特性是能够注册自定义序列化器和反序列化器。
    // 自定义序列化器和反序列化器在输入或输出JSON响应的结构与必须被序列化或反序列化的Java类不同的情况下非常有用
    // 这个自定义序列化器可以这样调用：
    @Test
    public void objectMapperTest7() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Car.class, new CustomCarSerializer());
        mapper.registerModule(module);
        Car car = new Car("yellow", "renault");
        String carJson = mapper.writeValueAsString(car);
        // 这是Car在客户端的样子（作为 JSON 输出）：
        // var carJson = {"car_brand":"renault"}
    }


    // 可以通过以下方式调用此自定义反序列化器：
    @Test
    public void objectMapperTest8() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);
        Car car = mapper.readValue(json, Car.class);
    }


    // 处理日期格式
    // java.util.Date 的默认序列化会生成一个数字，即纪元时间戳（自 1970 年 1 月 1 日起的毫秒数，UTC）。但这不是人类可读的，需要进一步转换才能以人类可读的格式显示。
    @Test
    public void objectMapperTest9() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = new Car("yellow", "renault");
        final Request request = new Request();
        request.setCar(car);
        request.setDatePurchased(new Date());
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);
        final String carAsString = objectMapper.writeValueAsString(request);
        // output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"2016-07-03 11:43 AM CEST"}
        // 要了解有关使用 Jackson 序列化日期的更多信息，请阅读我们更深入的文章(https://www.baeldung.com/jackson-serialize-dates)。
    }


    // 处理集合
    // DeserializationFeature类提供的另一个小而有用的功能是能够从 JSON 数组响应生成我们想要的集合类型。
    // 例如，我们可以将结果生成为数组：
    @Test
    public void objectMapperTest10() throws JsonProcessingException {
        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Car[] cars = objectMapper.readValue(jsonCarArray, Car[].class);
        // print cars
        // 或作为列表：
        String jsonCarArray1 =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        ObjectMapper objectMapper2 = new ObjectMapper();
        List<Car> listCar = objectMapper2.readValue(jsonCarArray1, new TypeReference<List<Car>>(){});
        // print cars
    }

    //  结论
    // Jackson 是一个可靠且成熟的 Java JSON 序列化/反序列化库。所述ObjectMapper API提供一个简单的方法有很多的灵活性解析和生成JSON响应对象。本文讨论了使库如此受欢迎的主要功能。
}
