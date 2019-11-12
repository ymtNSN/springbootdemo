package com.ymt.springbootdemo.demo.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by @author yangmingtian on 2019/11/12
 */
public class MyConsumer {
    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    private final static  String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("Waiting for message....");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("Received message:" + msg);
                System.out.println("consumerTag:" + consumerTag);
                System.out.println("deliveryTag:" + envelope.getDeliveryTag());
            }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
