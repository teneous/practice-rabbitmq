package com.trifail.practice.api.advance.dlx;

import com.rabbitmq.client.*;
import com.trifail.practice.api.Constant;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;
import static com.trifail.practice.api.Constant.ADV_DLX_EXCHANGE;

/**
 * 死信队列-消费者
 * 消息过期或者投递失败等会进入次队列
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        final Channel channel = connection.createChannel();

        /*死信队列的名字*/
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(DLX_EXCHANGE_ARGS, ADV_DLX_EXCHANGE);
//        arguments.put(DLX_ROUTING_ARGS, "#");

        channel.exchangeDeclare(ADV_DLX_NORMAL_EXCHANGE, Constant.TOPIC, true, false, null);
        channel.queueDeclare(ADV_DLX_NORMAL_QUEUE, true, false, false, arguments);
        channel.queueBind(ADV_DLX_NORMAL_QUEUE, ADV_DLX_NORMAL_EXCHANGE, ADV_DLX_NORMAL_ROUTING_KEY);

        /*声明死信队列*/
        channel.exchangeDeclare(ADV_DLX_EXCHANGE, TOPIC, true, false, null);
        channel.queueDeclare(ADV_DLX_QUEUE, true, false, false, null);
        /*routingKey为任意匹配*/
        channel.queueBind(ADV_DLX_QUEUE, ADV_DLX_EXCHANGE, "#");

        rejectHandle(channel);
    }

    private static void rejectHandle(Channel channel) throws IOException {
        /*设置的是手动签收，我们拒绝签收，从而移动到私信队列*/
        channel.basicConsume(ADV_DLX_NORMAL_QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到消息:" + new String(body));
                channel.basicNack(envelope.getDeliveryTag(), false, false);
            }
        });
    }
}
