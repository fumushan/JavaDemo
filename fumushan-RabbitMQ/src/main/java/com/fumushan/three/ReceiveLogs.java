package com.fumushan.three;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:04:07
 */
public class ReceiveLogs {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		// 连接RabbitMQ服务器并创建通信通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/**
		 * 声明类型为fanout的交换机
		 */
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		/**
		 * 声明队列名称(这里为随机命名),并将队列绑定到交换机。队列启动后可以一直从交换机中获取到发布的信息
		 */
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(queueName+":等待接受消息");

		//消费者获取信息并处理
		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [*] Received '" + message + "'");
			}
		};
		
		//订阅消息
		channel.basicConsume(queueName, true, consumer);
	}
}