package listener.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布者与broker通信，无论失败还是成功都需要确认
 */
public class Producer {

    static String exchangeName = "test.confirm.exchange";
    static String routingKey = "confirm.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();

        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();

        /*设置消息确认*/
        channel.confirmSelect();

        /*发送消息*/
        channel.basicPublish(exchangeName, routingKey, null, "call me later if you see that.".getBytes());

        /*添加确认监听*/
        channel.addConfirmListener(
                /*如果成功*/
                ((deliveryTag, multiple) -> {
                    System.out.println("确认成功");
                    System.out.println(deliveryTag);
                    System.out.println(deliveryTag);
                }),
                /*如果失败*/
                ((deliveryTag, multiple) -> {
                    System.out.println("确认失败");
                    System.out.println(deliveryTag);
                    System.out.println(multiple);
                }));
    }
}
