package com.es.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-redis.xml","classpath:spring/applicationContext-rabbitmq.xml"})
public class RabbitMQTest {
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Test
    public void sendMessage() {
        MessageProperties properties = new MessageProperties();

        String allReceived = "小明说你好对小黄在5点钟";
        Message message1 = new Message(allReceived.getBytes(), properties);
        rabbitTemplate.send("topic_msg", "one.to.one.msg", message1);
	}
}
