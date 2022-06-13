import org.springframework.web.client.RestTemplate;


/**
 * 要删除现有资源，您可以使用delete()方法：
 */
public class RestTemplateDeleteTest {
    private RestTemplate restTemplate;


    private static final String fooResourceUrl = "http://localhost:" + "APPLICATION_PORT" + "/spring-rest/foos";
    private static final String FOO_RESOURCE_URL = "http://localhost:" + 8082 + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + 8082 + "/spring-rest";

    // 8. 使用 DELETE 删除资源

    public void deletePost() {
        Integer id =3;
        String entityUrl = fooResourceUrl + "/" + id;
        restTemplate.delete(entityUrl);
    }

    public void deletePost2() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

        // send DELETE request to delete post with `id` 10
        this.restTemplate.delete(url, 10);
    }


}
