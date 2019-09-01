package com.trifail.practice.boot.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Receiver {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*此处和producer的声明一致*/
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "${spring.rabbitmq.listener.customized.queue.name}",
                    type = "${spring.rabbitmq.listener.customized.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.customized.ignoreDeclarationExcetion}",
                    durable = "${spring.rabbitmq.listener.customized.exchange.durable}"),
            value = @Queue(value = "${spring.rabbitmq.listener.customized.queue.name}",
                    durable = "${spring.rabbitmq.listener.customized.queue.durable}"),
            key = {"${spring.rabbitmq.listener.customized.routingkey}"}
    ))
    @RabbitHandler
    public void receive(Message message, Channel channel) throws IOException {
        String messageId = (String) message.getHeaders().get(AmqpHeaders.MESSAGE_ID);
        Long deliverTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("消息内容" + message.getPayload().toString());
        System.out.println("消息Id:" + messageId + "," + "消息tag:" + deliverTag);
        channel.basicAck(deliverTag, false);
    }

    @RabbitHandler
    public void receive(@Payload String source, Channel channel, @Header Map<String, Object> headers) throws IOException {
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("消息内容" + source);
        System.out.println("消息tag:" + deliverTag);
        channel.basicAck(deliverTag, false);
    }

}
