package com.trifail.practice.api.advance.reachable.confirmed;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.ADV_CONFIRM_EXCHANGE;

/**
 * 消息确认模式，无论成功还是失败，broker将发送一个消息确认事件给发送者
 * 消息发送者--msg-->Broker
 * Broker--confirm-->消息发送者
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();
        Channel channel = connection.createChannel();

        /*设置消息确认*/
        channel.confirmSelect();
        /*发送消息*/
        channel.basicPublish(ADV_CONFIRM_EXCHANGE, "adv.confirm.test", null, "RSVP,from broker".getBytes());
        /*添加确认监听*/
        channel.addConfirmListener(
                /*如果成功*/
                ((deliveryTag, multiple) -> {
                    System.out.println("消息确认成功，消息已发送Broker");
                }),
                /*如果失败*/
                ((deliveryTag, multiple) -> {
                    System.out.println("消息确认失败，消息未送达Broker");
                }));
        /*通道不可以关掉，否则可能收不到回调确认*/
//        channel.close();
//        connection.close();
    }
}
