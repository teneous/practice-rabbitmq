package com.trifail.practice.api.advance.reachable.confirmed;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import static com.trifail.practice.api.Constant.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息确认模式，无论成功还是失败，broker将发送一个消息确认事件给发送者
 * 消息发送者--msg-->Broker
 * Broker--confirm-->消息发送者
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ADV_CONFIRM_EXCHANGE, TOPIC, true);
        channel.queueDeclare(ADV_CONFIRM_QUEUE, true, false, false, null);
        channel.queueBind(ADV_CONFIRM_QUEUE, ADV_CONFIRM_EXCHANGE, ADV_CONFIRM_ROUTING_KEY);

        channel.basicConsume(ADV_CONFIRM_QUEUE, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println("--------------");
                System.out.println("consumerTag:" + consumerTag);
                System.out.println("envelope:" + envelope.toString());
                System.out.println("body:" + new String(body));
                System.out.println("--------------");
            }
        });
    }
}
