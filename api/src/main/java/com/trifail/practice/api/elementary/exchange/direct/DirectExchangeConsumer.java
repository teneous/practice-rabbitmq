package com.trifail.practice.api.elementary.exchange.direct;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * 如何声明PTP交换机
 * PTP要求消费端和生产端的routingkey一致
 */
public class DirectExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionCenterFactory.getConnectFactory();
        /*是否开启自动连接*/
        factory.setAutomaticRecoveryEnabled(true);
        /*每3秒进行一次远程连接*/
        factory.setNetworkRecoveryInterval(3000);
        Connection connection = ConnectionCenterFactory.getConnection(factory);
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ELE_DIRECT_EXCHANGE, DIRECT, true, false, false, null);
        channel.queueDeclare(ELE_DIRECT_QUEUE, true, false, false, null);
        channel.queueBind(ELE_DIRECT_QUEUE, ELE_DIRECT_EXCHANGE, ELE_DIRECT_ROUTINGKEY);

        //创建消费者
        channel.basicConsume(ELE_DIRECT_QUEUE, true, new DefaultConsumer(channel) {
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
