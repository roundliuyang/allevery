package com.yly.utils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.yly.config.RocketMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MQ工具类
 * @author xuzhipeng
 * @date 2021/3/19
 */
@Slf4j
@Component
public class MQUtil {

    @Resource
    private ProducerBean producerBean;
//    @Resource
    private RocketMQProperties rocketMQProperties;

    /**
     * 发送延迟消息
     * @param tag
     * @param key 设置代表消息的业务关键属性，请尽可能全局唯一。以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。（ 注意：不设置也不会影响消息正常收发。）
     * @param content 消息体
     * @param delayTime 延迟时间，单位毫秒（ms）,当前时间多少毫秒后执行
     */
    public SendResult sendDelayMessage(String tag,String key,String content,long delayTime){
        return sendDelayMessage(rocketMQProperties.getTopic(),tag,key,content,delayTime);
    }

    /**
     * 发送延迟消息
     * @param topic
     * @param tag
     * @param key 设置代表消息的业务关键属性，请尽可能全局唯一。以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。（ 注意：不设置也不会影响消息正常收发。）
     * @param content 消息体
     * @param delayTime 延迟时间，单位毫秒（ms）,当前时间多少毫秒后执行
     */
    private SendResult sendDelayMessage(String topic,String tag,String key,String content,long delayTime){
        Message message = new Message();
        message.setKey(key);
        message.setBody(content.getBytes());
        message.setTopic(topic);
        message.setTag(tag);
        message.setStartDeliverTime(System.currentTimeMillis()+delayTime);
        return sendMessage(message);
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    private SendResult sendMessage(Message message) {
        log.info("生产者发送消息到MQ消息,topic:[{}],tag:[{}],key:[{}],body:[{}]",message.getTopic(),message.getTag(),message.getKey(),new String(message.getBody()));
        SendResult sendResult = producerBean.send(message);
        log.info("生产者发送消息结果,[{}]",sendResult);
        return sendResult;
    }
}
