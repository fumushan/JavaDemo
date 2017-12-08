package com.fumushan.four;

/**
 * RabbitMQ之路由
 * 
 * 队列在绑定交换机的时候，即在channel.queueBind(queueName, EXCHANGE_NAME, routerKey)时再增加一个识别的关键字来细分
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:03:40
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "logs_direct";

	public static void main(String[] argv) throws Exception {
		// 连接RabbitMQ服务器并创建通信通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明类型为direct的交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		// 向交换机发布消息
		 String severity = "bule";
		 String message = getMessage(argv);
		
		channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
		System.out.println("EmitLog发布消息:" + severity + ":" + message);
		// 完成之后关闭通道,断开连接
		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 2)
			return "Hello World!";
		return joinStrings(strings, " ", 1);
	}

	private static String joinStrings(String[] strings, String delimiter, int startIndex) {
		int length = strings.length;
		if (length == 0)
			return "";
		if (length < startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
