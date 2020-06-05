package com.es.es_im.service.imOneToOne.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.es_im.service.BaseService;
import com.es.es_im.service.imOneToOne.OneToOneService;
import com.es.es_im.service.rabbitmq.RabbitMQProducerService;
@Service(value = "OneToOneService")
public class OneToOneServiceImpl  extends BaseService implements OneToOneService {
	
	@Autowired
	private RabbitMQProducerService rabbitMQProducerService;
	
	@Override
	public void saveMessage(String message) {

		rabbitMQProducerService.sendMsg(message, "1");
		
	}

}
