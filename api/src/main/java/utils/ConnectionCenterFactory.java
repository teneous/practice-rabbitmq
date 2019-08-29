package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionCenterFactory {

    public static Connection getConnection(ConnectionFactory factory) throws IOException, TimeoutException {
        return factory.newConnection();
    }

    public static ConnectionFactory getConnectFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("111.231.69.73");
        factory.setPort(5672);
        factory.setVirtualHost("my_vhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory;
    }
}
