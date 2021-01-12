package com.pc.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者
 *
 * @author pc
 * @Date 2021/1/12
 **/
@Slf4j
@Component
public class Consumer {

	/***
	 *  场景1.1   消息被(basic.reject() or basic.nack()) and requeue = false，即消息被消费者拒绝或者nack，并且重新入队为false。
	 *  场景1.2   消费者设置了自动签收，当重复投递次数达到了设置的最大retry次数之后，消息也会投递到死信队列，但是内部的原理还是调用了nack/reject。
	 *
	 */

	/**
	 * 正常用户队列消息监听消费者
	 *
	 * @param user
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = "${app.rabbitmq.queue.user}")
	public void userConsumer(User user, Message message, Channel channel) {
		log.info("正常用户业务监听：接收到消息:[{}]", JSON.toJSONString(user));
		try {
			//nack()与reject()的区别是：reject()不支持批量拒绝，而nack()可以.
			//参数为：消息的DeliveryTag，是否批量拒绝，是否重新入队
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			log.info("拒绝签收...消息的路由键为:[{}]", message.getMessageProperties().getReceivedRoutingKey());
		} catch (IOException e) {
			log.error("消息拒绝签收失败", e);
		}
	}

	/**
	 * @param user
	 * @param message
	 * @param channel
	 */
	@RabbitListener(queues = "${app.rabbitmq.queue.user-dead-letter}")
	public void userDeadLetterConsumer(User user, Message message, Channel channel) {
		log.info("接收到死信消息:[{}]", JSON.toJSONString(user));
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("死信队列签收消息....消息路由键为:[{}]", message.getMessageProperties().getReceivedRoutingKey());
		} catch (IOException e) {
			log.error("死信队列消息签收失败", e);
		}
	}

}
