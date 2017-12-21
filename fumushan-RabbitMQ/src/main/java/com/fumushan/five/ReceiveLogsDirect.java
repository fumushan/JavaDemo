package com.fumushan.five;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * RabbitMQ之主题topic
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:04:07
 */
public class ReceiveLogsDirect {

	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) throws Exception {
		// 连接RabbitMQ服务器并创建通信通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明类型为topic的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);  
       
        // 生成临时队列并获取队列名称
        String queueName = channel.queueDeclare().getQueue();  
        /**
         * 对于主题类型的交换机，队列在获取消息时,通过设置主题来接收与kernel相关的消息  
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "kernal.*"); 
		System.out.println(queueName + ":等待接受标识为kernal的相关消息");

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [*] Received '" + message + "'");
			}
		};

		// 订阅消息
		channel.basicConsume(queueName, true, consumer);
	}

}