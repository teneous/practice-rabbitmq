package exchange.direct;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionCenterFactory.getConnectFactory();

        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);
        Connection connection = ConnectionCenterFactory.getConnection(factory);
        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange";
        String exchangeType = "direct";
        String routingKey = "test.direct";
        String queueName = "test_direct_queue";
        //声明一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //声明一个队列
        channel.queueDeclare(queueName, true, false, false, null);
        //绑定队列交换器
        channel.queueBind(queueName, exchangeName, routingKey);


        //创建消费者
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String routingKey = envelope.getRoutingKey();

                String contentType = properties.getContentType();

                long deliveryTag = envelope.getDeliveryTag();
                System.out.println(new String(body));
                channel.basicAck(deliveryTag, true);
            }
        });

    }
}
