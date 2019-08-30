package com.trifail.practice.api.elementary.exchange.topic;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import static com.trifail.practice.api.Constant.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 如何声明SUB/PUB交换机
 * routingKey可以使用#(任意)或者*(单)
 */
public class TopicExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ELE_TOPIC_EXCHANGE, TOPIC, true, false, false, null);
        channel.queueDeclare(ELE_TOPIC_QUEUE, false, false, false, null);
        channel.queueBind(ELE_TOPIC_QUEUE, ELE_TOPIC_EXCHANGE, ELE_TOPIC_ROUTINGKEY);


        //创建消费者
        channel.basicConsume(ELE_TOPIC_QUEUE, true, new DefaultConsumer(channel) {
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
