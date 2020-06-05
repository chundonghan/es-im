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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.es.es_im.common.util.ApplicationContextRegister;
import com.es.es_im.common.util.DateUtil;
import com.es.es_im.service.redis.RedisService;

/**
 * 记录在线用户
 * @author handch
 *
 */
@Component
@Scope("prototype")
@ServerEndpoint(value = "/online/{account}",configurator=SpringConfigurator.class)
public class ImOnline {
	private Logger logger = LogManager.getLogger();
	
	//ApplicationContext act = ApplicationContextRegister.getApplicationContext();
	protected static ConcurrentHashMap<String, ImOnline> onlineClients= new ConcurrentHashMap<String, ImOnline>();
	
	@Autowired
	private RedisService redisService;
	
	private Session session;
	
	@OnOpen
	public void onOpen(@PathParam("account") String account,Session session, EndpointConfig config) {
		this.session = session;
		
		Boolean userOnline = redisService.userOnline(account);
		if(userOnline) {
			//sendMessage("succ");
			onlineClients.put(account, this);
		}
	}
	
	@OnClose
	public void onClose(@PathParam("account") String account) {
		onlineClients.remove(account);
		redisService.userOffline(account);
	}
	
	// a@b:msg$msg_group#time?id
	// a@groupb/g:msg$msg_group#time?id
	@OnMessage
	public void onMessage(String message, Session session) {
		String from = message.substring(0,message.indexOf("@"));
		String to = message.substring(message.indexOf("@")+1,message.indexOf(":"));
		String msg = message.substring(message.indexOf(":")+1);
		//from:msg$msg_group#time?id
		msg =from+":"+msg;
		ImOnline imOnline = onlineClients.get(to);
		if(imOnline != null) {
			this.sendMessage(msg);
		}
		
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
	
	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			
		}
	}
}
