package com.ay.rabbitmq.simpleQueue;


import com.ay.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String QUEUE_NAME = "simple_queue";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("new api recieve:"+msg);
            }
        };

        //坚挺队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }


    /**
     * 老的api
     * @param args
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void oldApi(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

       //定义队列的消费者
        //3.4版本mq
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //消费者监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String msg = new String(delivery.getBody());

            System.out.println("<--recieve msg:"+msg+"-->");
        }
    }
}
