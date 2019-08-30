package com.trifail.practice.api.advance.reachable.returned;

import com.rabbitmq.client.*;
import com.trifail.practice.api.utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.trifail.practice.api.Constant.ADV_RETURN_ERROR_ROUTING_KEY;
import static com.trifail.practice.api.Constant.ADV_RETURN_EXCHANGE;

/**
 * 消息不可达机制，当消息不可达(exchange不存在或routingkey路由不到时)，会收到returnlistener的回调。
 * 注意mandatory必须设置为true，为false时broker会丢弃该消息
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();
        Channel channel = connection.createChannel();

        /*错误的路由key,此处的mandatory必须为true*/
        channel.basicPublish(ADV_RETURN_EXCHANGE, ADV_RETURN_ERROR_ROUTING_KEY, true, null, "check return message".getBytes());

        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            System.out.println("replayCode:" + replyCode + ",replyText:" + replyText + ",exhcange:"
                    + exchange + ",routingKey" + routingKey + ",properties:" + properties + ",body:" + new String(body));
        });
        /*通道不可以关掉，否则可能收不到回调确认*/
        channel.close();
        connection.close();
    }
}
