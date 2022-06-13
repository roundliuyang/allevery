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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
    在本文中，您将学习如何在 Spring Boot 应用程序中使用RestTemplate类发出不同类型的 HTTP POST 请求。
    HTTP POST 请求用于创建新资源。该类RestTemplate提供了几个模板方法，如postForObject()、postForEntity()和 ，postForLocation()用于发出 POST 请求。
    前两种方法与我之前在RestTemplate 的 GET 请求教程中讨论的非常相似。最后一个方法返回新创建的资源的位置，而不是返回完整的资源。
 */
public class RestTemplatePostTest {
    private RestTemplate restTemplate;

    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + 8082 + "/spring-rest";



    //使用POST创建资源为了在 API 中创建新的 Resource，我们可以充分利用postForLocation()、postForObject()或postForEntity() API。第一个返回新创建的资源的 URI，而第二个返回资源本身。



//    -----------------------------------------------------------------------------------------------------------------

    /**
     * 要使用 RestTemplate 发出简单的 HTTP POST 请求，您可以使用该postForEntity方法并将请求正文参数作为映射对象传递：
     */
    @Test
    public void simple_post() {
        // request url
        String url = "https://reqres.in/api/users";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // request body parameters
        Map<String, String> map = new HashMap<>();
        map.put("name", "John Doe");
        map.put("job", "Java Developer");

        // send POST request
        ResponseEntity<Void> response = restTemplate.postForEntity(url, map, Void.class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
        } else {
            System.out.println("Request Failed");
        }

    }

    /**
     * 要使用 JSON 请求正文发出 POST 请求，我们需要将Content-Type【请求标头】设置为application/json. 以下示例演示如何使用 JSON 请求正文发出 HTTP POST 请求：
     */
    @Test
    public void json_and_header_post() {
        // request url
        String url = "https://jsonplaceholder.typicode.com/posts";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        map.put("title", "Spring Boot 101");
        map.put("body", "A powerful tool for building web apps.");

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        // 也可以返回一个对象
//        ResponseEntity<Post> response = this.restTemplate.postForEntity(url, entity, Post.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

    }

    /**
     * 带有基本身份验证的 POST 请求
     * 以下示例演示如何向 RestTemplate POST 请求添加基本身份验证：
     */
    @Test
    public void json_and_basicAuth_post() {
        // request url
        String url = "https://reqres.in/api/login";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // add basic authentication
        headers.setBasicAuth("username", "password");

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login Failed");
        }
    }


    /**
     * 带有映射到 Java 对象的响应的 POST 请求
     * RestTemplate 允许您将 JSON 响应直接映射到 Java 对象。让我们首先创建一个简单的Post类：
     */
    @Test
    public void json_and_object_post() {
        // request url
        String url = "https://jsonplaceholder.typicode.com/posts";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        Post post = new Post(101, 1, "Spring Boot 101",
                "A powerful tool for building web apps.");

        // build the request
        HttpEntity<Post> request = new HttpEntity<>(post, headers);

        // send POST request
        ResponseEntity<Post> response = restTemplate.postForEntity(url, request, Post.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Post Created");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

    }


    //  Submit Form Data
    // 接下来，让我们看看如何使用 POST 方法提交表单。

    @Test
    public void givenFooService_whenFormSubmit_thenResourceIsCreated() {

        //首先，我们需要将Content-Type标头设置为application/x-www-form-urlencoded
        //这确保可以将大查询字符串发送到服务器，其中包含由&分隔的 名称/值对：
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 我们可以将表单变量包装到LinkedMultiValueMap 中：
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", "10");

        // 接下来，我们使用HttpEntity实例构建请求 ：
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // Finally, we can connect to the REST service by calling restTemplate.postForEntity() on the Endpoint: /foos/form,此接口返回一个字符串（见：tutorials FooController）。
        ResponseEntity<String> response = restTemplate.postForEntity( fooResourceUrl+"/form", request , String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        final String fooResponse = response.getBody();
        assertThat(fooResponse, notNullValue());
        assertThat(fooResponse, is("10"));
    }



//-------------------------------------------------------------------------------------------------------------------

    // The postForObject() API
    @Test
    public void givenFooService_whenPostForObject_thenCreatedObjectIsReturned() {
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        final Foo foo = restTemplate.postForObject(fooResourceUrl, request, Foo.class);
        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("bar"));
    }
    // The postForObject() API
    public Post createPostWithObject() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        Post post = new Post();

        // build the request
        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        // send POST request
        return restTemplate.postForObject(url, entity, Post.class);
    }


//---------------------------------------------------------------------------------------------------------------------

    // The postForLocation() API
    // 它没有返回完整的资源，而只是返回新创建的资源的位置。
    @Test
    public void givenFooService_whenPostForLocation_thenCreatedLocationIsReturned() {
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        final URI location = restTemplate.postForLocation(fooResourceUrl, request, Foo.class);
        assertThat(location, notNullValue());
    }






}
