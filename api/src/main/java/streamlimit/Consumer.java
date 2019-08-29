package streamlimit;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 限流
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        String exchange = "test_qos_exchange";
        String queue = "qos.1";
        String routingKey = "qos.*";
        //创建消费者
        channel.exchangeDeclare(exchange, "topic", true, false, null);
        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchange, routingKey);

        /*设置限流，第三个参数表示针对的是这个消费者而不是channel*/
        channel.basicQos(0, 3, false);
        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                /*消费完需要进行ack确认，如果不确认，是不会从queue中拉取新信息*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
