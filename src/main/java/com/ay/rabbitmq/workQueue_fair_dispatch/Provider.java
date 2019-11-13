package com.ay.rabbitmq.workQueue_fair_dispatch;

import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * fair dispatch 公平分发
 */
public class Provider {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        /** durable参数改为true持久化（但是不能重新定义已存在的队列）
         * channel.queueDeclare(QUEUE_NAME,durable=true,false,false,null)
         *
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //每个消费者返回确认消息之前，消息队列不发送下一个消息给消费者，一次只处理一个消息，限制同一个消费者不能消费超过一条消息
        int prefetch = 1;
        channel.basicQos(prefetch);


        for (int i =0;i<50;i++) {
            String msg = "Hello,work_queue"+ i;
            System.out.println("provider 发送消息"+i);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*5);
        }

        channel.close();
        connection.close();
    }
}
