package com.yly.uniform.all.comtroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HandlerInterceptorAdapterDemoController {

    @GetMapping(value = "/getAddress")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getUserAddress(@RequestAttribute("name") String name) {
        ArrayList<String> list = new ArrayList<>();
        list.add("唐家三星");
        return list;
    }

}
