package com.yly.domain.service;


import com.yly.domain.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public Message deliverMessage (Message message) {

        return message;
    }
}
