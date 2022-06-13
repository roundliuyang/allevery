package com.yly.resttemplate;

import java.io.Serializable;

public class Post implements Serializable {

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post() {
    }

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    // getters and setters, equals(), toString() .... (omitted for brevity)
}