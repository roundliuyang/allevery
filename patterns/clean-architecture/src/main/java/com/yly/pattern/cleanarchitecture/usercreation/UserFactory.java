package com.yly.pattern.cleanarchitecture.usercreation;

interface UserFactory {
    User create(String name, String password);
}
