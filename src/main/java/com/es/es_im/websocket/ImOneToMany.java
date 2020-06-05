package com.es.es_im.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.es.es_im.common.util.DateUtil;
import com.es.es_im.common.util.SnowflakeIdWorker;
import com.es.es_im.dao.im.ImDao;
import com.es.es_im.dao.redis.RedisDao;
import com.es.es_im.service.rabbitmq.RabbitMQProducerService;
@Component
@Scope("prototype")
@ServerEndpoint(value = "/oneToMany/{group}/{member}",configurator=SpringConfigurator.class)
public class ImOneToMany {
    
    @Autowired
	private  RabbitMQProducerService rabbitMQProducerService;
    @Autowired
	private RedisDao redisDao;
    @Autowired
    private ImDao imDao;
	// 组对应在线成员容器
	private static ConcurrentHashMap<String, CopyOnWriteArraySet<ImOneToMany>> oneToManyClients = new ConcurrentHashMap<String, CopyOnWriteArraySet<ImOneToMany>>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private String group;
	private String member;
	
	@OnOpen
	public void onOpen(@PathParam("group") String group,@PathParam("member") String member,Session session, EndpointConfig config) {
		this.session = session;
		this.group = group;
		this.member = member;
		/*判断 组 以及 成员 是否存在并有效*/
		
		/* 如果组成员在线人数为0  创建一个装载成员容器,如果成员不为空 则使用原有容器装载成员*/
		if (oneToManyClients.isEmpty()) {
			CopyOnWriteArraySet<ImOneToMany> chatClient = new CopyOnWriteArraySet<ImOneToMany>();
			chatClient.add(this);
			oneToManyClients.put(group, chatClient);
		} else {
			CopyOnWriteArraySet<ImOneToMany> chatClient = oneToManyClients.get(group);
			if(chatClient == null) {
				chatClient = new CopyOnWriteArraySet<ImOneToMany>();
			}
			
			chatClient.add(this);
			oneToManyClients.put(group, chatClient);
		}
	}
	
	@OnClose
	public void onClose(@PathParam("group") String group) {
		CopyOnWriteArraySet<ImOneToMany> memberSets = oneToManyClients.get(group);
		if(memberSets.contains(this)) {
			memberSets.remove(this);
		}
	}
	
	@OnMessage
	public void onMessage(@PathParam("group") String group,@PathParam("member") String member,String message, Session session) {
		/* 窗口发送消息*/
		CopyOnWriteArraySet<ImOneToMany> memberSets = oneToManyClients.get(group);
		Iterator<ImOneToMany> iterator = memberSets.iterator();
		ImOnline imOnline = null;
		String dateStr = DateUtil.date2String(new Date(),"HH:mm");
		long id = new SnowflakeIdWorker(0, 0).nextId();
		String new_message = message +"#"+dateStr+"?"+id;
		while(iterator.hasNext()) {
			ImOneToMany next = iterator.next();
			// 不给同组自己推送信息
			if(next.group.equals(group) && next.member.equals(member)) {
				continue;
			}
			next.sendMessage(member+":"+new_message);
			
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("group_id", group);
		List<Map<String, Object>> groupAccountList = imDao.getGroupAccountList(params);
		for(Map<String,Object> groupAccount : groupAccountList) {
			String user_account = groupAccount.get("user_account").toString();
			/*消息列表推送消息*/
			imOnline = ImOnline.onlineClients.get(user_account);
			// 对方在线
			if(imOnline != null) {
				// a@groupb/g:msg$msg_group#time?id
				imOnline.onMessage(group+"@"+user_account+":"+new_message, session);
			}
			//保存新消息标志到缓存中
			redisDao.hset(group,user_account, "true");
		}
		
		/*记录消息*/
		rabbitMQProducerService.sendMsg(member+"@"+group+"/g"+":"+message+"?"+id, "2");
	}
	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
}
