package com.ay.rabbitmq.simpleQueue;

import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 提供者发送消息
 */
public class Provider {

    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection conn = ConnectionUtil.getConnection();

        Channel channel = conn.createChannel();

        //定义队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "Hello,simple queue !";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("<--sent message:"+msg+" -->");

        channel.close();

        conn.close();
    }
}
