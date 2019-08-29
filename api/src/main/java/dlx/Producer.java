package dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列
 */
public class Producer {

    static String exchangeName = "test_dlx_normal_exchange";
    static String routingKey = "dlx.normal.save";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();
        String msg = "Hello RabbitMQ DLX Message";


        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .contentEncoding("UTF_8")
                .expiration("10000").deliveryMode(2).build();
        channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
    }
}
