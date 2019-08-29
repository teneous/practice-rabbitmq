package ack;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 重回队列
 */
public class Producer {

    static String exchangeName = "test_ack_exchange";
    static String routingKey = "ack.save";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();
        String msg = "Hello RabbitMQ ACK Message";


        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> header = new HashMap<>();
            header.put("num", i);
            /*准备配置信息*/
            AMQP.BasicProperties properties =
                    new AMQP.BasicProperties.Builder().deliveryMode(2).contentEncoding("UTF_8").headers(header).build();

            channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
