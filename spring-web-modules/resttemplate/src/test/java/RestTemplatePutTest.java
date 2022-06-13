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
 * RestTemplate 类提供headForHeaders()检索标头的方法：
 */
public class RestTemplatePutTest {
    private RestTemplate restTemplate;


    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + 8082 + "/spring-rest";

    //  7.使用 PUT 更新资源
    // 接下来，我们将开始研究PUT，更具体地说，是这个操作的exchange()API，因为template.put API是非常简单明了的

    @Test
    public void givenFooService_whenPutExistingEntity_thenItIsUpdated() {
        final HttpHeaders headers=null;             //= prepareBasicAuthHeaders();
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"), headers);

        // Create Resource
        final ResponseEntity<Foo> createResponse = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

        // Update Resource
        final Foo updatedInstance = new Foo("newName");
        updatedInstance.setId(createResponse.getBody()
                .getId());
        final String resourceUrl = fooResourceUrl + '/' + createResponse.getBody()
                .getId();
        final HttpEntity<Foo> requestUpdate = new HttpEntity<>(updatedInstance, headers);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);

        // Check that Resource was updated
        final ResponseEntity<Foo> updateResponse = restTemplate.exchange(resourceUrl, HttpMethod.GET, new HttpEntity<>(headers), Foo.class);
        final Foo foo = updateResponse.getBody();
        assertThat(foo.getName(), is(updatedInstance.getName()));
    }

    public void updatePost() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

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

        // send PUT request to update post with `id` 10
        this.restTemplate.put(url, entity, 10);
    }

    /**
     * 该put()方法不返回任何内容。如果要处理响应，请改用通用exchange()方法：
     */
    public Post updatePostWithResponse() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

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

        // send PUT request to update post with `id` 10
        ResponseEntity<Post> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, Post.class, 10);

        // check response status code
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }




}
