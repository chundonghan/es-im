package com.es.es_im.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
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
import com.es.es_im.dao.redis.RedisDao;
import com.es.es_im.service.redis.RedisService;

/**
 * 推送添加好友申请
 * @author handch
 *
 */
@Component
@Scope("prototype")
@ServerEndpoint(value = "/contact/{account}",configurator=SpringConfigurator.class)
public class ImContact {
	private Logger logger = LogManager.getLogger();
	
	protected static ConcurrentHashMap<String, ImContact> contactClients= new ConcurrentHashMap<String, ImContact>();
	@Autowired
	private RedisDao redisDao;
	
	private Session session;
	
	@OnOpen
	public void onOpen(@PathParam("account") String account,Session session, EndpointConfig config) {
		this.session = session;
		contactClients.put(account, this);
	}
	
	@OnClose
	public void onClose(@PathParam("account") String account) {
		contactClients.remove(account);
	}
	
	//
	@OnMessage
	public void onMessage(@PathParam("account") String from_account,String message, Session session) {
		String to_account = message;// 对方账号
		ImContact imContact = contactClients.get(to_account);
		if(imContact != null) {
			imContact.sendMessage("new_apply");
		}
		redisDao.lpush("apply_to_"+to_account, from_account);
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
