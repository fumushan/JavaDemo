package com.fumushan.five;

/**
 * RabbitMQ之主题topic
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月19日 下午4:03:40
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import java.util.UUID;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "topic_logs";
	
	public static void main(String[] argv) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明类型为topic的交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

		/**
		 * 设置路由绑定键
		 * 
		 * 发往主题类型的转发器的消息不能随意的设置选择键（routingkey），必须是由点隔开的一系列的标识符组成。标识符可以是任何东西，但是一般都与消息的某些特性相关。
		 * 一些合法的选择键的例子："stock.usd.nyse", "nyse.vmw","quick.orange.rabbit".你可以定义任何数量的标识符，上限为255个字节。绑定键和选择键的形式一样。主
		 * 题类型的转发器背后的逻辑和直接类型的转发器很类似：一个附带特殊的选择键将会被转发到绑定键与之匹配的队列中。
		 * 需要注意的是：关于绑定键有两种特殊的情况。
		 * 			*可以匹配一个标识符。
		 * 			#可以匹配0个或多个标识符。
		 *   		例如： animal.* 可以匹配animal.rabbit、animal.tigar等。而animal.#可以匹配animal.birds.magpie等
		 */
		String[] routing_keys = new String[] { "kernal.info", "cron.warning", "auth.info", "kernal.critical" };
		for (String routing_key : routing_keys) {
			String message = UUID.randomUUID().toString();
			channel.basicPublish(EXCHANGE_NAME, routing_key, null, message.getBytes());
			System.out.println("【*】 发送routingKey = " + routing_key + " ,message = " + message + ".");
		}

		channel.close();
		connection.close();
	}

}
