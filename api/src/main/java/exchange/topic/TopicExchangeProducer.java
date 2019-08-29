package exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();
        //名字声明
        String exchangeName = "test_topic_exchange";
        String routingKey = "user.a";
        String routingKey2 = "user.ab";
        String routingKey3 = "userabc";

        channel.basicPublish(exchangeName, routingKey, null, "消息1发送".getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, "消息2发送".getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, "消息3发送".getBytes());
        channel.close();
        connection.close();
    }
}
