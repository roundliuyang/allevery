package com.yly.springcloud.rabbitmqdemo.producerdemo.mesage;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    @Output("demo01-output")
    MessageChannel demo01Output();

}
