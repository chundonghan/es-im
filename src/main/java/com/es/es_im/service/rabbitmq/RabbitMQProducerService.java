package com.es.es_im.service.rabbitmq;


public interface RabbitMQProducerService {
	/**
	 * 
	 * @param msg 消息内容
	 * @param msg_type 消息类型 1私聊  2群聊
	 */
	public void sendMsg(String msg,String msg_type);
}
