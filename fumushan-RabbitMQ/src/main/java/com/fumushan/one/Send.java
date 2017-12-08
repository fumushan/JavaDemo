package com.fumushan.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {

		/**
		 * 连接RabbitMQ服务器
		 * 创建连接通道。本例连接本机的消息服务器实体(localhost)，如果想连接其它主机上的RabbitMQ服务，只需要修改一下主机名或是IP
		 */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/**
		 * 声明一个消息消息队列，
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/**
		 * 向消息队列里推送消息 
		 * 声明一个幂等的队列（只有在该队列不存在时，才会被创建）。消息的上下文是一个 字节数组，你可以指定它的编码。
		 */
		String message = "Hello World asdfsdafrewfasdfsad!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println("Send：" + message);

		/**
		 * 关闭连接通道
		 */
		channel.close();
		connection.close();
	}
}
