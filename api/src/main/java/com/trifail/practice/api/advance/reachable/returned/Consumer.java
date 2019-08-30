package com.trifail.practice.api.advance.reachable.returned;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.ADV_RETURN_EXCHANGE;
import static com.trifail.practice.api.Constant.ADV_RETURN_QUEUE;
import static com.trifail.practice.api.Constant.ADV_RETURN_ROUTING_KEY;
import static com.trifail.practice.api.Constant.TOPIC;

/**
 * 消息不可达机制，当消息不可达(exchange不存在或routingkey路由不到时)，会收到returnlistener的回调。
 * 注意mandatory必须设置为true，为false时broker会丢弃该消息
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ADV_RETURN_EXCHANGE, TOPIC, true);
        channel.queueDeclare(ADV_RETURN_QUEUE, true, false, false, null);
        channel.queueBind(ADV_RETURN_QUEUE, ADV_RETURN_EXCHANGE, ADV_RETURN_ROUTING_KEY);

        channel.basicConsume(ADV_RETURN_QUEUE, true, new DefaultConsumer(channel) {
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
