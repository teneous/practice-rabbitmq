package listener.confirm;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    static String exchangeName = "test.confirm.exchange";
    static String routingKey = "confirm.#";
    static String type = "topic";
    static String queueName = "test.confirm.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();

        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, type, true);

        channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag:" + consumerTag);
                System.out.println("envelope:" + envelope.getDeliveryTag());
                System.out.println("消费端:" + new String(body));
            }
        });
    }
}
