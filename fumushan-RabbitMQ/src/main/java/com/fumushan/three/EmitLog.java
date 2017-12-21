package com.fumushan.three;

/**
 * RabbitMQ之交换机Exchangs
 * 
 * 生产者可以向交换机中发布信息，之后在消费者中创建队列，并将队列和某个将还击绑定。
 * 之后消费者就可以从交换机中获取到生产者发布的信息。
 * 
 * 接收消息，转发消息到绑定的队列。四种类型：direct, topic, headers and fanout。可以从BuiltinExchangeType中获取
 * direct：转发消息到routigKey指定的队列
 * topic：按规则转发消息（最灵活）
 * headers：（这个还没有接触到）
 * fanout：转发消息到所有绑定队列
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:03:40
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

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
		 * 向交换机发布消息
		 */
		String message = getMessage(argv);
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		
		System.out.println("EmitLog发布消息成功");

		// 完成之后关闭通道,断开连接
		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1) {
			return "info: Hello World!";
		}
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0) {
			return "";
		}
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
