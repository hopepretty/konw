package com.pc.rabbitmq;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * 测试接口
 *
 * @author pc
 * @Date 2021/1/12
 **/
@RestController
public class TestControlller {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping("init")
	public void init() {
		System.out.println(rabbitTemplate.isRunning());
	}

	@Value("${app.rabbitmq.exchange.user}")
	private String userExchange;

	@RequestMapping("send")
	public void send() {
		String exp = "3000";
		User user = new User()
				.setUserName("天文")
				.setAddress("浙江杭州")
				.setBirthday(LocalDate.now(ZoneOffset.ofHours(8)));
		//
		rabbitTemplate.convertAndSend(userExchange, "user.abc", user, message -> {
			MessageProperties messageProperties = message.getMessageProperties();
			//为每条消息设定过期时间
			messageProperties.setExpiration(exp);
			return message;
		});
	}

}
