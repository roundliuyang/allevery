package com.yly.pattern.cleanarchitecture.usercreation;

public interface UserInputBoundary {
    UserResponseModel create(UserRequestModel requestModel);
}
