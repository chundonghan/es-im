package com.es.es_im.service.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.es.es_im.dao.mongodb.MongoDBDao;
import com.es.es_im.service.BaseService;
import com.es.es_im.websocket.ImOneToOne;

@Service(value = "MongoDBService")
public class MongoDBService extends BaseService{

	@Autowired
	private MongoDBDao mongoDBDao;
	
	public void addToChatMarket(ImOneToOne imOneToOne) {
		mongoDBDao.insert(imOneToOne);
	}
	
	public ImOneToOne getImOneToOneFromChatMarket(String value) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("chat_link").is(value));
		Query query = new Query(criteria);
		ImOneToOne oneToOne = mongoDBDao.findOne(query, ImOneToOne.class);
		return oneToOne;
	}
	
	public void removeFromChatMarket(String value) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("chat_link").is(value));
		Query query = new Query(criteria);
		mongoDBDao.delete(query, ImOneToOne.class);
	}
}
