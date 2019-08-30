package com.trifail.practice.api.elementary.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import static com.trifail.practice.api.Constant.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 如何声明SUB/PUB交换机
 * routingKey可以使用#(任意)或者*(单)
 */
public class TopicExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        channel.basicPublish(ELE_TOPIC_EXCHANGE, "ele.topic.msg1", null, "topic消息1发送".getBytes());
        channel.basicPublish(ELE_TOPIC_EXCHANGE, "ele.topic.msg2", null, "topic消息2发送".getBytes());
        channel.basicPublish(ELE_TOPIC_EXCHANGE, "ele.topic.msg3", null, "topic消息3发送".getBytes());
        channel.close();
        connection.close();
    }
}
