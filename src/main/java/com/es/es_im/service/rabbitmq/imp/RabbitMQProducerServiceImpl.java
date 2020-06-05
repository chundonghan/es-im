package com.es.es_im.service.rabbitmq.imp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.es_im.dao.im.ImDao;
import com.es.es_im.service.BaseService;
import com.es.es_im.service.rabbitmq.RabbitMQProducerService;

@Service(value = "RabbitMQProducer")
public class RabbitMQProducerServiceImpl  extends BaseService implements RabbitMQProducerService{
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	

	
	/**
	 * 
	 * @param msg 消息内容
	 * @param msg_type 消息类型 1私聊  2群聊
	 */
	public void sendMsg(String msg,String msg_type) {
		MessageProperties properties = new MessageProperties();
        Message message = new Message(msg.getBytes(), properties);
        String routingKey = "";
        if("1".equals(msg_type)) {
        	routingKey = "one.to.one.msg";
        }
        if("2".equals(msg_type)) {
        	routingKey = "one.to.many.msg";
        }
        rabbitTemplate.send("topic_msg",routingKey , message);
	}
	

}
