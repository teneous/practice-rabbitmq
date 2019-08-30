package com.trifail.practice.api.advance.qos;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * 限流发生在消费端，有channel和queue两种
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        //创建消费者
        channel.exchangeDeclare(ADV_QOS_EXCHANGE, TOPIC, true, false, null);
        channel.queueDeclare(ADV_QOS_QUEUE, true, false, false, null);
        channel.queueBind(ADV_QOS_QUEUE, ADV_QOS_EXCHANGE, ADV_QOS_ROUTING_KEY);

        /*
         * 第一参数:文件大小
         * 第二参数：一次消费多少消息
         * 第三参数：true:针对channel，false针对queue
         */
        channel.basicQos(0, 3, false);
        /*设置手动ack*/
        channel.basicConsume(ADV_QOS_QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息体接受:" + new String(body));
                /*消费完需要进行ack确认，如果不确认，是不会从queue中拉取新信息*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
