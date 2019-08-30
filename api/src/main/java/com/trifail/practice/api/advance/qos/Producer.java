package com.trifail.practice.api.advance.qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * 限流发生在消费端，有channel和queue两种
 */
public class Producer {


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();
        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(ADV_QOS_EXCHANGE, "adv.qos.test", null, ("数据标识：" + i).getBytes());
        }
        channel.close();
        connection.close();
    }
}
