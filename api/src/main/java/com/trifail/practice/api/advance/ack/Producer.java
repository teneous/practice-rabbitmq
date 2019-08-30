package com.trifail.practice.api.advance.ack;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;
import static com.trifail.practice.api.Constant.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 消息手动确认/拒绝机制-生产者
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();
        String msg = "ACK Message";

        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> header = new HashMap<>();
            header.put("current message num:", i);
            /*准备配置信息*/
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF_8")
                    .headers(header)
                    .build();
            channel.basicPublish(ADV_ACK_EXCHANGE, "adv.ack.test", properties, msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
