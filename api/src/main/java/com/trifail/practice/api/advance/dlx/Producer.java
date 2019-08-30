package com.trifail.practice.api.advance.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * 死信队列-生产者
 * 消息过期或者投递失败等会进入次队列
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();
        /*请搭配对应consumer方法使用*/
//        timeoutPublish(channel);
        rejectPublish(channel);
        connection.close();
    }

    /*设置8秒过期，此时需要关闭客户端*/
    private static void timeoutPublish(Channel channel) throws IOException, TimeoutException {
        String msg = "DLX TimeOut Message";
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .contentEncoding("UTF_8")
                /*设置8秒过期，如果8秒内没有被消费则会被移动到死信队列*/
                .expiration("8000")
                .deliveryMode(2).build();
        /*发送两条消息*/
        channel.basicPublish(ADV_DLX_NORMAL_EXCHANGE, "adv.dlx.test", properties, msg.getBytes());
        channel.close();
    }

    /*测试客户拒绝签收,并不允许重回队列*/
    private static void rejectPublish(Channel channel) throws IOException, TimeoutException {
        String msg = "DLX Reject Message";
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .contentEncoding("UTF_8")
                .expiration("8000")
                .deliveryMode(2).build();
        /*发送两条消息*/
        channel.basicPublish(ADV_DLX_NORMAL_EXCHANGE, "adv.dlx.test", properties, msg.getBytes());
        channel.close();
    }
}
