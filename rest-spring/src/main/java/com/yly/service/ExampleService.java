package com.yly.service;


import com.yly.requestresponsebody.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public boolean fakeAuthenticate(LoginForm lf) {
        return true;
    }
}