package com.es.es_im.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.es.es_im.common.util.DateUtil;
import com.es.es_im.common.util.SnowflakeIdWorker;
import com.es.es_im.dao.redis.RedisDao;
import com.es.es_im.service.rabbitmq.RabbitMQProducerService;

@Component
@Scope("prototype")
@ServerEndpoint(value = "/oneToOne/{chat_link}",configurator=SpringConfigurator.class)
public class ImOneToOne{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Session session;
	private static ConcurrentHashMap<String, ImOneToOne> oneToOneClients= new ConcurrentHashMap<String, ImOneToOne>();
	@Autowired
	private RedisDao redisDao;
	
    @Autowired
	private  RabbitMQProducerService rabbitMQProducerService;
    
	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("chat_link") String chat_link,Session session, EndpointConfig config) {
		this.session = session;
		oneToOneClients.put(chat_link, this);
		
	}
	
	@OnClose
	public void onClose(@PathParam("chat_link") String chat_link) {
		oneToOneClients.remove(chat_link);
	}
	
	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(@PathParam("chat_link") String chat_link,String message, Session session) {
		String from = chat_link.substring(0,chat_link.indexOf("@"));
		String to = chat_link.substring(chat_link.indexOf("@")+1);
		String reverse_link = getReverseChatLink(chat_link);
		String dateStr = DateUtil.date2String(new Date(),"HH:mm");
		long id = new SnowflakeIdWorker(0, 0).nextId();
		//a@b:msg$msg_group#time?id
		String new_message = message +"#"+dateStr+"?"+id;
		ImOneToOne oneToOne = oneToOneClients.get(reverse_link);
		//私聊同时在窗口
		if(oneToOne != null) {
			oneToOne.sendMessage(new_message);
		}
		
		ImOnline imOnline = ImOnline.onlineClients.get(to);
		// 对方在线
		if(imOnline != null) {
			imOnline.onMessage(chat_link+":"+new_message, session);
		}
		//保存新消息标志到缓存中
		redisDao.hset(from, to, "true");
		//a@b:msg$msg_group?id
		rabbitMQProducerService.sendMsg(chat_link+":"+message+"?"+id, "1");
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("error");
		error.printStackTrace();
	}
	public void sendMessage(String message){
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getReverseChatLink(String chat_link) {
		String[] from_to = chat_link.split("@");
		String from = from_to[0];
		String to  = from_to[1];
		String key = to+"@"+from;
		return key;
	}
}
