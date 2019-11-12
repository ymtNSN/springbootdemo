package com.ymt.springbootdemo.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * Created by @author yangmingtian on 2019/11/12
 */
public class TransactionProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String msg = "this is transaction mq";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("消息已经回滚");
        }
        channel.waitForConfirmsOrDie();
        channel.close();
        conn.close();
    }
}
