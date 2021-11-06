package com.yly.resttemplate;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Author: yly
 * Date: 2021/11/6 16:14
 *
 * 从 Spring Framework 5 开始，除了 WebFlux 堆栈之外，Spring 还引入了一个名为WebClient的新 HTTP 客户端。
 * WebClient 是RestTemplate的现代替代 HTTP 客户端。它不仅提供了传统的同步 API，而且还支持高效的非阻塞和异步方法。
 * 也就是说，如果我们正在开发新应用程序或迁移旧应用程序，最好使用 WebClient。展望未来，RestTemplate 将在未来版本中弃用。
 */
public class resttemplateTest {

    //使用Get检索资源

    // 获取纯 JSON
    @Test
    public  void test(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/spring-rest/foos";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
    }

    /*
        请注意，我们拥有对 HTTP 响应的完全访问权限，因此我们可以执行诸如检查状态代码以确保操作成功或使用响应的实际正文之类的操作：
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        assertThat(name.asText(), notNullValue());
        我们在这里将响应正文作为标准字符串使用，并使用 Jackson（以及 Jackson 提供的 JSON 节点结构）来验证一些细节。
     */

    // Retrieving POJO Instead of JSON





}
