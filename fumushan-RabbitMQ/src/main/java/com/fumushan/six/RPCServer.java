package com.fumushan.six;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * RabbitMQ(六)远程调用模式之服务端
 * 
 * @author FUMUSHAN
 * @datetime 2017年12月21日 上午10:40:13
 */
public class RPCServer {

	private static final String RPC_QUEUE_NAME = "rpc_queue";

	private static int fib(int n) {
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		return fib(n - 1) + fib(n - 2);
	}

	public static void main(String[] argv) throws Exception {

		// 连接RabbitMQ服务器并创建频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

		// 设置队列每次只处理一个，没处理完之前不继续换另一个队列处理
		channel.basicQos(1);

		// 重写消费者。接受消息并进行处理
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body) throws IOException {
				AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
				String response = "";
				try {
					String message = new String(body, "UTF-8");
					int n = Integer.parseInt(message);

					System.out.println(" [.] fib(" + message + ")");
					response += fib(n);
				} catch (RuntimeException e) {
					e.printStackTrace();
				} finally {
					
					//将收到的消息进行处理，再一次发布消息
					channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
					channel.basicAck(envelope.getDeliveryTag(), false);
					synchronized (this) {
						this.notify();
					}
				}
			}
		};

		// 订阅消息
		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

		// 使用线程处理并发问题
		while (true) {
			synchronized (consumer) {
				try {
					consumer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}