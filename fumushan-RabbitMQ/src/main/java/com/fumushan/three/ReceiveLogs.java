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
 * RabbitMQ之交换机Exchangs
 * 
 * 生产者可以向交换机中发布信息，之后在消费者中创建队列，并将队列和某个将还击绑定。
 * 之后消费者就可以从交换机中获取到生产者发布的信息。
 * 
 * 接收消息，转发消息到绑定的队列。四种类型：direct, topic, headers and fanout
 * direct：转发消息到routigKey指定的队列
 * topic：按规则转发消息（最灵活）
 * headers：（这个还没有接触到）
 * fanout：转发消息到所有绑定队列
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:03:40
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
		 * 声明队列名称(这里从临时队列中获取该临时队列的名称),并将队列绑定到交换机。队列启动后可以一直从交换机中获取到发布的信息
		 * channel.queueDeclare()指的是临时队列
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