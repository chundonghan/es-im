package com.es.es_im.service.redis;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.es_im.common.util.Constants;
import com.es.es_im.common.util.SerializeUtil;
import com.es.es_im.dao.redis.RedisDao;
import com.es.es_im.service.BaseService;
import com.es.es_im.websocket.ImOneToOne;
@Service(value = "RedisService")
public class RedisService extends BaseService{
	@Autowired
	private RedisDao redisDao;
	
	/**
	 * 用户上线（添加用户到集合）
	 * @param username
	 */
	public Boolean userOnline(String username) {
		
		Boolean is_online = redisDao.sismember("online_users", username);
		if(!is_online) {
			redisDao.sadd(Constants.ONLINE_USERS, username);
		}
		is_online = redisDao.sismember(Constants.ONLINE_USERS, username);
		if(is_online) {
			logger.debug("用户"+username+"上线成功");
			Set<String> smembers = redisDao.smembers(Constants.ONLINE_USERS);
			
			Iterator<String> iterator = smembers.iterator();
			while(iterator.hasNext()) {
				String exist_online_user = iterator.next();
				if(exist_online_user.equals(username)) {
					continue;
				}
				String chat_link_key = username + "@" + exist_online_user;
				redisDao.sadd(Constants.CHAT_LINK, chat_link_key);
				chat_link_key = exist_online_user + "@" + username;
				redisDao.sadd(Constants.CHAT_LINK, chat_link_key);
			}
			
		}else {
			logger.debug("用户"+username+"上线失败");
		}
		
		return is_online;
	}
	/**
	 * 用户下线（删除集合中用户）
	 * @param username
	 */
	public void userOffline(String username) {
		redisDao.srem(Constants.ONLINE_USERS, username);
		
		Set<String> smembers = redisDao.smembers(Constants.CHAT_LINK);
		
		Iterator<String> iterator = smembers.iterator();
		while(iterator.hasNext()) {
			String chat_link = iterator.next();
			if(chat_link.startsWith(username+"@")) {
				redisDao.srem(Constants.CHAT_LINK, chat_link);
			}else {
				continue;
			}
		}
		
		
		
		logger.debug("用户"+username+"下线");
	}
	
}
