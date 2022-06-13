import com.yly.resttemplate.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * RestTemplate 类提供put()可用于更新资源的方法：
 */
public class RestTemplateHeadersTest {
    private RestTemplate restTemplate;


    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";


    // HEAD
    @Test
    public void givenFooService_whenCallHeadForHeaders_thenReceiveAllHeaders() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        final HttpHeaders httpHeaders = testRestTemplate.headForHeaders(FOO_RESOURCE_URL);
        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
    }


}
