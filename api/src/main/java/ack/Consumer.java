package ack;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 重复队列
 */
public class Consumer {

    static String exchangeName = "test_ack_exchange";
    static String routingKey = "ack.#";
    static String queue = "test.ack.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        //创建消费者
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchangeName, routingKey);

        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                Integer num = (Integer) properties.getHeaders().get("num");
                System.out.println(new String(body) + ":" + num);
                if (num == 0) {
                    /*最后一个参数代表是否重回队列,如果消费失败会重新放在队尾*/
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });

    }
}
