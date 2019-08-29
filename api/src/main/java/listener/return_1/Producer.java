package listener.return_1;

import com.rabbitmq.client.*;
import utils.ConnectionCenterFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    static String exchangeName = "test.return.exchange";
    static String errorRoutingKey = "error.error";
//    static String correctRoutingKey = "return.error";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectFactory = ConnectionCenterFactory.getConnectFactory();
        Connection connection = connectFactory.newConnection();

        Channel channel = connection.createChannel();


        channel.basicPublish(exchangeName, errorRoutingKey, true, null, "check return message".getBytes());

        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {

            System.out.println("replayCode:" + replyCode + ",replyText:" + replyText + ",exhcange:"
                    + exchange + ",routingKey" + routingKey + ",properties:" + properties + ",body:" + new String(body));
        });

    }
}
