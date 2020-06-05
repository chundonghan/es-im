package com.es.es_im.dao.mongodb;

import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongoDBDao {
	private final Logger logger = LoggerFactory.getLogger(MongoDBDao.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(Object t) {
		mongoTemplate.insert(t);
		
	}
	
	public <E> E findOne(Query query,Class<E> clazz) {
		E obj = mongoTemplate.findOne(query, clazz);
		return obj;
	}
	
	public void  delete(Query query,@SuppressWarnings("rawtypes") Class clazz) {
		mongoTemplate.remove(query, clazz);
	}
}
