package com.yly.pattern.cleanarchitecture.usercreation;

interface UserRegisterDsGateway {
    boolean existsByName(String identifier);

    void save(UserDsRequestModel requestModel);
}
