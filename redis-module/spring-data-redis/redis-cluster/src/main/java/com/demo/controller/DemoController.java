package com.demo.controller;


import com.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping
public class DemoController {

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/putKey")
    @ResponseBody
    public String putKey(@RequestBody User user) {
        if (StringUtils.isEmpty(user)) {
            return "key must not empty";
        } else {
            Random random = new Random();
            int i = random.nextInt(1000) + 1;
            String key = "user" + i;

            if (redisUtil.set(key, user, 300)) {
                return "success";
            }
        }
        return "fail";
    }

    @PostMapping("/getKey")
    @ResponseBody
    public String getKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return "key must not empty";
        } else {
            User user = (User) redisUtil.get(key);
            return user.toString();
        }
    }

}
