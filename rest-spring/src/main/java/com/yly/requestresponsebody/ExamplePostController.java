package com.yly.requestresponsebody;


import com.yly.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class ExamplePostController {

    private static Logger log = LoggerFactory.getLogger(ExamplePostController.class);

    @Autowired
    ExampleService exampleService;


    // Simply put, the @RequestBody annotation maps the HttpRequest body to a transfer or domain object,
    // enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
    // Spring会自动将JSON反序列化为一个Java类型，前提是指定了一个合适的类型
    // 默认情况下，我们使用 @RequestBody 注解的类型必须对应于从我们的客户端控制器发送的 JSON：

    @PostMapping("/request")
    public ResponseEntity postController(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        exampleService.fakeAuthenticate(loginForm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // @ResponseBody 注释告诉控制器返回的对象会自动序列化为 JSON 并传递回 HttpResponse 对象
    // 请记住，我们不需要使用 @ResponseBody 注释来注释 @RestController 注释的控制器，因为 Spring 默认情况下会这样做。
    //  https://www.baeldung.com/spring-request-response-body

    @PostMapping("/response")
    @ResponseBody
    public ResponseTransfer postResponseController(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("Thanks For Posting!!!");
    }


    // 当我们使用@ResponseBody注解时，我们仍然能够明确地设置我们方法返回的内容类型。
    // 为此，我们可以使用@RequestMapping的 produces 属性。注意，像@PostMapping、@GetMapping等注解为该参数定义了别名。
    // 在示例中，我们使用了 MediaType.APPLICATION_JSON_VALUE 常量。或者，我们可以直接使用 application/json。
    @PostMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseTransfer postResponseJsonContent(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("JSON Content!");
    }

    // 接下来，让我们实现一个新的方法，映射到相同的/content路径，但返回XML内容
    @PostMapping(value = "/content", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseTransfer postResponseXmlContent(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("XML Content!");
    }

    // 现在，根据请求标头中发送的 Accept 参数的值，我们将得到不同的响应。
    //让我们看看这个：
//    curl -i \
//            -H "Accept: application/json" \
//            -H "Content-Type:application/json" \
//            -X POST --data
//  '{"username": "johnny", "password": "password"}' "https://localhost:8080/.../content"
//    CURL命令返回一个JSON响应。
//    HTTP/1.1 200
//    Content-Type: application/json
//    Transfer-Encoding: chunked
//    Date: Thu, 20 Feb 2020 19:43:06 GMT
//
//    {"text":"JSON Content!"}
//
}