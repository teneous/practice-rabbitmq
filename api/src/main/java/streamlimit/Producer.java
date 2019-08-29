package streamlimit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费端限流
 */
public class Producer {

    static String exchangeName = "test_qos_exchange";
    static String routingKey = "qos.save";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, null, ("there a plenty" + i).getBytes());
        }
        channel.close();
        connection.close();
    }
}
