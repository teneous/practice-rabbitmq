package com.trifail.practice.api.elementary.exchange.fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * fanout交换机没有路由key，凡是连接到对应交换机的queue都可以接受到信息
 */
public class FanoutExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ELE_FANOUT_EXCHANGE, FANOUT, true, false, false, null);
        channel.queueDeclare(ELE_FANOUT_QUEUE, false, false, false, null);
        channel.queueBind(ELE_FANOUT_QUEUE, ELE_FANOUT_EXCHANGE, "");

        //创建消费者
        channel.basicConsume(ELE_FANOUT_QUEUE, true, new DefaultConsumer(channel) {
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
