package com.ay.rabbitmq.directExchange;

import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class provider {
    private static final String EXCHANGE_NAME= "exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机  fanout:不处理路由键
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //发送消息
        String msg = "你好，direct directExchange!";

        //direct:直连路由
        String routingkey = "info";
        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());

        System.out.println("生产者发送消息："+msg);

        channel.close();
        connection.close();

    }

}
