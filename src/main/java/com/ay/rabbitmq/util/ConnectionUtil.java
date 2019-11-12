package com.ay.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.omg.CORBA.TIMEOUT;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    /**
     * 获取连接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义链接工厂
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_user1");
        factory.setUsername("user1");
        factory.setPassword("123456");
        return factory.newConnection();
    }
}
