import com.yly.resttemplate.Foo;
import com.yly.resttemplate.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Spring Framework 中的 RestTemplate 类是一个同步 HTTP 客户端，用于发出 HTTP 请求以使用 RESTful Web 服务。它公开了一个简单易用的模板方法 API，
 * 用于发送 HTTP 请求并处理 HTTP 响应。RestTemplate 类还为所有受支持的 HTTP 请求方法提供别名，例如 GET、POST、PUT、DELETE 和 OPTIONS。
 * 在本教程中，我们将学习如何使用 Spring REST 客户端 - RestTemplate - 在 Spring Boot 应用程序中发送 HTTP 请求。对于我们所有的示例，我们将使用JSONPlaceholder假 REST API 来模拟真实的应用场景。
 */
public class RestTemplateGetTest {
    private RestTemplate restTemplate;


    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + 8082 + "/spring-rest";

    //使用Get检索资源

    /**
     * 获取纯 JSON
     * 注意该getForObject()方法返回的响应。它是一个纯JSON字符串。我们可以使用 Jackson轻松地将这个 JSON 字符串解析为一个对象。
     */
    @Test
    public  void test() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/spring-rest/foos";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
        System.out.println(response);
        Thread.sleep(80L);

    }


    /**
     * 获取响应作为对象
     * 我们还可以将响应直接映射到模型类。让我们首先创建一个模型类：
     * 现在我们可以简单地将Post类用作getForObject()方法中的响应类型：
     */
    @Test
    public  Post[] test1() throws InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/posts";
        return this.restTemplate.getForObject(url, Post[].class);
    }



    /**
     * 响应处理
     * 如果要操作响应（例如检查 HTTP 状态代码），请改用getForEntity()如下方法：
     */
    @Test
    public Post getPostWithResponseHandling() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";
        ResponseEntity<Post> response = this.restTemplate.getForEntity(url, Post.class, 1);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }


    /**
     * 自定义请求标头
     * 如果要设置请求标头，如content-type,accept或任何自定义标头，请使用通用exchange()方法：
     */
    @Test
    public Post getPostWithCustomHeaders() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // set custom header
        headers.set("x-request-source", "desktop");

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // use `exchange` method for HTTP call
        ResponseEntity<Post> response = this.restTemplate.exchange(url, HttpMethod.GET, request, Post.class, 1);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }


    /*
        请注意，我们拥有对 HTTP 响应的完全访问权限，因此我们可以执行诸如检查状态代码以确保操作成功或使用响应的实际正文之类的操作：
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        assertThat(name.asText(), notNullValue());
        我们在这里将响应正文作为标准字符串使用，并使用 Jackson（以及 Jackson 提供的 JSON 节点结构）来验证一些细节。

        Retrieving POJO Instead of JSON
        我们还可以将响应直接映射到资源 DTO：
     */
    @Test
    public void givenTestRestTemplate_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Foo> response = testRestTemplate.getForEntity(FOO_RESOURCE_URL + "/1", Foo.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }




    // The exchange() API
    // Let's have a look at how to do a POST with the more generic exchange API:

    @Test
    public void test2(){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        ResponseEntity<Foo> response = restTemplate
                .exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        Foo foo = response.getBody();

        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("bar"));
    }



//         9. 配置超时
//         我们可以通过简单地使用ClientHttpRequestFactory来配置RestTemplate超时：

    //    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
    // 我们可以使用HttpClient进行进一步的配置选项：

/*
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }
 */


    // 10。  ParameterizedType
    /**
     假设接收请求的类如下。
     public class ResultClass {
     private long idx;
     private String name;
     }
     如何以列表形式获得回报 - 存在问题。不建议
     List<Resultclass> list = restTemplate.getForObject("url", List.class);
     这种方法是有问题的。
     编译没有问题，但是调用后查看list 值，可以看到它有一个LinkedHashMap对象，而不是ResultClass对象。
     所以，如果你像下面这样调用它，你调用它的那一刻就会发生错误。
     List<Resultclass> list = restTemplate.getForObject("url", List.class);
     ResultClass resultClass = list.get(0);
     resultClass.getIdx();
     如果返回为List<map<Object, Object>>并被使用，可以这样调用，但不建议再次转换，不方便。

     在交换中使用 ParameterizedTypeReference
     springframework 3.2之后，支持ParameterizedTypeReference，可以如下使用。
     ResponseEntity<List<ResultClass>> response = restTemplate.exchange("url",HttpMethod.GET, null, new ParameterizedTypeReference<List<ResultClass>>() {});
     List<ResultClass> list = response.getBody();
     同样的，非数组情况同样适用
     */


    // 11. 以数组形式返回
     /*
         如果使用 xxxForObject 方法，如下所示。
         ResultClass[] resultClasses = restTemplate.getForObject("url", ResultClass[].class); List<Resultclass> list = Arrays.asList(resultClasses);

         如果使用 xxxForEntity，则如下。
         ResponseEntity<ResultClass[]> responseEntity = restTemplate.getForEntity("url", ResultClass[].class);
         List<Resultclass> list = Arrays.asList(responseEntity.getBody());
     */
}
