import com.yly.resttemplate.Foo;
import com.yly.resttemplate.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * 让我们使用optionsForAllow()方法来获取所有支持的 HTTP 操作的列表：
 */
public class RestTemplateOptionsTest {
    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";

    private RestTemplate restTemplate;

    //  使用 OPTIONS 获取允许的操作
    // Next, we're going to have a quick look at using an OPTIONS request and exploring the allowed operations on a specific URI using this kind of request;
    // the API is optionsForAllow:

    @Test
    public void givenFooService_whenCallOptionsForAllow_thenReceiveValueOfAllowHeader() {
        final Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);
        final HttpMethod[] supportedMethods = { HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD };

        assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
    }


}
