package com.trifail.practice.api.advance.ack;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;
import static com.trifail.practice.api.Constant.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 消息手动确认/拒绝机制-消费者
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        final Channel channel = connection.createChannel();

        /*创建一个持久化交换机*/
        channel.exchangeDeclare(ADV_ACK_EXCHANGE, TOPIC, true, false, null);
        /*创建一个非独占非自动删除的队列*/
        channel.queueDeclare(ADV_ACK_QUEUE, true, false, false, null);
        /*交换机队列绑定*/
        channel.queueBind(ADV_ACK_QUEUE, ADV_ACK_EXCHANGE, ADV_ACK_ROUTING_KEY);
        /*设置消费者,手动签收*/
        channel.basicConsume(ADV_ACK_QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Integer num = (Integer) properties.getHeaders().get("current message num:");
                System.out.println("消费者:收到消息内容:" + new String(body) + ",它是第" + num + "条消息");
                /*让第一条数据走nack*/
                if (num == 0) {
                    /*最后一个参数代表是否重回队列,如果设置为true，则会在basicNack后重新进入队尾*/
                    channel.basicNack(envelope.getDeliveryTag(), false, false);
                    System.err.println("消息序号:" + num + ",消费失败");
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });
    }
}
