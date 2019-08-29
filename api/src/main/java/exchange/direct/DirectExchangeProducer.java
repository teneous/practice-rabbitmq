package exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();
        String exchangeName = "test_exchange";
        String routingKey = "test.direct";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, null, "hello rabbit mq".getBytes());
        }
        channel.close();
        connection.close();

    }
}
