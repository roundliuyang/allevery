package com.yly.junit5.mockito.repository;


import com.yly.junit5.mockito.User;

public interface MailClient {

    void sendUserRegistrationMail(User user);
    
}
