package com.yly.spring.transaction.example3;


import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    String value();

    String core = "core";

    String card = "card";
}