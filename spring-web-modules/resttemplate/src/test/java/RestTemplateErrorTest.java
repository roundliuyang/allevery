import com.yly.resttemplate.Foo;
import com.yly.resttemplate.Post;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * 如果请求执行过程中出现错误或者服务器返回不成功的 HTTP 错误（4xx 或 5xx），RestTemplate 会抛出异常。您可以捕获HttpStatusCodeExceptionincatch块以获取响应正文和标头
 */
public class RestTemplateErrorTest {
    private RestTemplate restTemplate;


    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + 8082 + "/spring-rest";

    public String unknownRequest() {
        try {
            String url = "https://jsonplaceholder.typicode.com/404";
            return this.restTemplate.getForObject(url, String.class);
        } catch (HttpStatusCodeException ex) {
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();
            System.out.println(headers.get("Content-Type"));
            System.out.println(headers.get("Server"));
        }

        return null;
    }





}
