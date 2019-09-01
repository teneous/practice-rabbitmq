package com.trifail.practice.boot.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*此处和producer的声明一致*/
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "exe.springboot.confirm", type = "topic", ignoreDeclarationExceptions = "true", durable = "true"),
            value = @Queue(value = "que.springboot.confirm", durable = "true", exclusive = "false", autoDelete = "false"),
            key = {"springboot.#"}
    ))
    @RabbitHandler
    public void receiver(Message message, Channel channel) throws IOException {
        String messageId = (String) message.getHeaders().get(AmqpHeaders.MESSAGE_ID);
        Long deliverTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("消息内通" + message.getPayload().toString());
        System.out.println("消息Id:" + messageId + "," + "消息tag:" + deliverTag);
        channel.basicAck(deliverTag, false);
    }

}
