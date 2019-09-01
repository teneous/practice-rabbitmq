package com.trifail.practice.boot.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.out.println("本消息的唯一码：" + correlationData);
        System.out.println("消息确认结构：" + ack);
        System.out.println("异常信息：" + cause);
        if (ack) {
            System.out.println("数据正常handler");
        }else {
            System.out.println("异常处理handler");
        }
    };

    private RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> {
        System.out.println("exchange = " + exchange);
        System.out.println("routingKey = " + routingKey);
        System.out.println("replyText = " + replyText);
        System.out.println("replyCode = " + replyCode);
        System.out.println("message = " + message);
    };


    public void send(Object source, Map<String, Object> properties) {
        MessageHeaders messageHeaders = new MessageHeaders(properties);

        Message message = MessageBuilder.createMessage(source, messageHeaders);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        rabbitTemplate.convertAndSend("exe.springboot.confirm",
                "springboot.hi", message, new CorrelationData(UUID.randomUUID().toString()));
    }
}
