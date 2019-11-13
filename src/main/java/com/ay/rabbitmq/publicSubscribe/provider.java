package com.ay.rabbitmq.publicSubscribe;

import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class provider {

    private static final String EXCHANGE_NAME= "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机  fanout:不处理路由键
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //发送消息
        String msg = "你好，Public subscribe!";

        //fanout:不处理路由键 s1="";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("生产者发送消息："+msg);

        channel.close();
        connection.close();
    }
}
