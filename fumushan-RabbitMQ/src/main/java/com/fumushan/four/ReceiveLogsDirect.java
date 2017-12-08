package com.fumushan.four;

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
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:04:07
 */
public class ReceiveLogsDirect {

	private static final String EXCHANGE_NAME = "logs_direct";

	public static void main(String[] argv) throws Exception {
		// 连接RabbitMQ服务器并创建通信通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明类型为direct的交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		
		
		/**
		 * 路由绑定键。在将队列绑定到某个交换机时，在增加一个识别字段。这样可以创建多个队列来绑定该交换机
		 * 问题：这边是一个消息队列还是三个消息队列？？？
		 */
		String[] severitys = {"orange","red","bule"};
		String queueName = channel.queueDeclare().getQueue();
		for (String severity : severitys) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
		}
		System.out.println(queueName + ":等待接受消息");

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [*] Received '" + message + "'");
			}
		};

		// 订阅消息
		channel.basicConsume(queueName, true, consumer);
	}

}