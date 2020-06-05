package com.es.es_im.common.config.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.es.es_im.common.util.SnowflakeIdWorker;
import com.es.es_im.dao.im.ImDao;
import com.rabbitmq.client.Channel;


/**
 * 声明队列、交换机、绑定关系、和队列消息监听
 * @author handch
 *
 */
@Configuration
public class RabbitMQChannel {
	
	@Autowired
	private ImDao imDao;

    @Bean
    public TopicExchange exchange() {
        // 创建一个持久化的交换机
        return new TopicExchange("topic_msg", true, false);
    }
    
	/**
	 * 私聊队列
	 * @return
	 */
	@Bean
    public Queue singleMsgQueue() {
        // 创建一个持久化的队列
        return new Queue("SingleMsgQueue", true);
    }
	/**
	 * 群聊队列
	 * @return
	 */
	@Bean
    public Queue groupMsgQueue() {
        // 创建一个持久化的队列
        return new Queue("GroupMsgQueue", true);
    }
	
    @Bean
    public Binding oneToOne() {
        return BindingBuilder.bind(singleMsgQueue()).to(exchange()).with("one.to.one.msg");
    }
    
    @Bean
    public Binding oneToMany() {
        return BindingBuilder.bind(groupMsgQueue()).to(exchange()).with("one.to.many.msg");
    }
    /*创建msg消费者监听*/
    @Bean
    public SimpleMessageListenerContainer singleMsgQueueLister(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置监听的队列
        container.setQueues(singleMsgQueue());
        // 指定要创建的并发使用者数。
        container.setConcurrentConsumers(1);
        // 设置消费者数量的上限
        container.setMaxConcurrentConsumers(5);
        // 设置是否自动签收消费 为保证消费被成功消费，建议手工签收
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				// 可以在这个地方得到消息额外属性
                MessageProperties properties = message.getMessageProperties();
                //得到消息体内容
                byte[] body = message.getBody();
                try {
                	Map<String, Object> msgMap = transformMsg(new String(body));
                    imDao.saveMessage(msgMap);
                }catch(Exception e){
                	e.printStackTrace();
                }finally {
                	/*
                     * DeliveryTag 是一个单调递增的整数
                     * 第二个参数 代表是否一次签收多条，如果设置为true,则所有DeliveryTag小于该DeliveryTag的消息都会被签收
                     */
                    channel.basicAck(properties.getDeliveryTag(), false);
                }
                
                
				
			}
			
        });
        return container;
    }
	
	/**
	 * 消息转换成map
	 * 私聊  a@b:msg$msg_group?id
	 * 
	 */
	private Map<String,Object> transformMsg(String message) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String from = message.substring(0,message.indexOf("@"));
		String to = message.substring(message.indexOf("@")+1,message.indexOf(":"));
		String msg = message.substring(message.indexOf(":")+1,message.lastIndexOf("$"));
		String msg_group = message.substring(message.lastIndexOf("$")+1,message.lastIndexOf("?"));
		String id = message.substring(message.lastIndexOf("?")+1);
		boolean is_group = to.endsWith("/g");
		if(!is_group) {
			retMap.put("id", id);
			retMap.put("from_account", from);
			retMap.put("to_account", to);
			retMap.put("msg", msg);
			retMap.put("msg_group", msg_group);
			
		}else {
			String group_id = to.substring(0, to.indexOf("/g"));
			retMap.put("id", id);
			retMap.put("user_account", from);
			retMap.put("group_id", group_id);
			retMap.put("msg", msg);
			retMap.put("msg_group", msg_group);
		}
		return retMap;
	}
	
    @Bean
    public SimpleMessageListenerContainer groupMsgQueueLister(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置监听的队列
        container.setQueues(groupMsgQueue());
        // 指定要创建的并发使用者数。
        container.setConcurrentConsumers(1);
        // 设置消费者数量的上限
        container.setMaxConcurrentConsumers(5);
        // 设置是否自动签收消费 为保证消费被成功消费，建议手工签收
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				// 可以在这个地方得到消息额外属性
                MessageProperties properties = message.getMessageProperties();
                //得到消息体内容
                byte[] body = message.getBody();
                try {
                	Map<String, Object> msgMap = transformMsg(new String(body));
                    imDao.saveGroupMessage(msgMap);
                }catch(Exception e){
                	e.printStackTrace();
                }finally {
                	/*
                     * 	DeliveryTag 是一个单调递增的整数
                     * 	第二个参数 代表是否一次签收多条，如果设置为true,则所有DeliveryTag小于该DeliveryTag的消息都会被签收
                     */
                    channel.basicAck(properties.getDeliveryTag(), false);
                }
				
			}
			
        });
        return container;
    }
}
