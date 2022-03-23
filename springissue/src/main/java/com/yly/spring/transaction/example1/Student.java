package com.yly.spring.transaction.example1;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private Integer id;
    private String realname;
}
