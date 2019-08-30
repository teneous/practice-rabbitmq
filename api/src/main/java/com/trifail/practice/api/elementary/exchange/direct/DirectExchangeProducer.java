package com.trifail.practice.api.elementary.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * 如何声明PTP交换机
 * PTP要求消费端和生产端的routingkey一致
 */
public class DirectExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(ELE_DIRECT_EXCHANGE, ELE_DIRECT_ROUTINGKEY, null, "Hello ,Direct Rabbitmq Msg".getBytes());
        }
        channel.close();
        connection.close();
    }
}
