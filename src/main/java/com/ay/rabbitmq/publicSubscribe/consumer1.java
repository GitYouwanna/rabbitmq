package com.ay.rabbitmq.publicSubscribe;

import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class consumer1 {

    private static final String QUEUE_NAME = "public_subscribe_queue1";
    private static final String EXCHANGE_NAME= "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        channel.basicQos(1);
        //定义消费者监听队列
        Consumer consumer = new DefaultConsumer(channel){
            //消息到达触发
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("【consumer1】 收到消息："+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("【consumer1】处理结束");
                    //消费完发送回执
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        /**
         * boolean autoAck = true（自动确认模式）默认为true
         * 表明一旦队列将消息分发给消费者，就将消息从内存中删除，这种情况下，如果正在执行的消费者突然宕机，将丢失消息
         * boolean autoAck = false（手动确认模式），如果其中一个消费者宕机，mq将发送给其他消费者
         */
        boolean autoAck = false;
        //自动应答改为false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }
}
