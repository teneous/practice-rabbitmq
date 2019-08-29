package dlx;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 普通配置
 */
public class Consumer {

    static String exchangeName = "test_dlx_normal_exchange";
    static String routingKey = "dlx.#";
    static String queue = "test.dlx_normal.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");

        //创建正常队列
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queue, true, false, false, arguments);
        channel.queueBind(queue, exchangeName, routingKey);

        //声明死信队列
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                Integer num = (Integer) properties.getHeaders().get("num");
                System.out.println(new String(body) + ":" + num);
            }
        });

    }
}
