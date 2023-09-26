package com.yly.spring.cloud.bootstrap.gateway.client.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// todo 使用注册中心
@FeignClient(value = "book-service",url ="http://127.0.0.1:8083")
public interface BooksClient {

    @RequestMapping(value = "/books/{bookId}", method = { RequestMethod.GET })
    Book getBookById(@PathVariable("bookId") Long bookId);
}
