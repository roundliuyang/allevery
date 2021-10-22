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
    //  https://www.baeldung.com/spring-request-response-body            todo

    @PostMapping("/response")
    @ResponseBody
    public ResponseTransfer postResponseController(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("Thanks For Posting!!!");
    }

    @PostMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseTransfer postResponseJsonContent(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("JSON Content!");
    }

    @PostMapping(value = "/content", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseTransfer postResponseXmlContent(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("XML Content!");
    }
}