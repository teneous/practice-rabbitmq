package com.trifail.practice.api.elementary.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.*;

/**
 * fanout交换机没有路由key，凡是连接到对应交换机的queue都可以接受到信息
 */
public class FanoutExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionCenterFactory.getConnection(ConnectionCenterFactory.getConnectFactory());
        Channel channel = connection.createChannel();

        channel.basicPublish(ELE_FANOUT_EXCHANGE, "ele.fanout.msg1", null, "fanout消息1发送".getBytes());
        channel.basicPublish(ELE_FANOUT_EXCHANGE, "ele.fanout.msg2", null, "fanout消息2发送".getBytes());
        channel.basicPublish(ELE_FANOUT_EXCHANGE, "ele.fanout.msg3", null, "fanout消息3发送".getBytes());
        channel.close();
        connection.close();
    }
}
