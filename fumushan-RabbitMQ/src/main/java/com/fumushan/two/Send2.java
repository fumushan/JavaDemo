package com.fumushan.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 消息队列
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午2:32:39
 */
public class Send2 {

	/**
	 * 队列名称
	 */
	private final static String TASK_QUEUE_NAME = "task_queue";

	private static String getMessage(String[] strings) {
		if (strings.length < 1) {
			return "Hello World...";
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

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/**
		 * 声明消息队列持久化
		 * 生产和消费者需要同步进行持久化
		 */
		boolean durable = true;
		channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
		//channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
		
		String message = getMessage(argv);
		channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
	}

}
