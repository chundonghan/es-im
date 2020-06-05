package com.es.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.es.es_im.common.config.mongodb.MongoDBConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MongoDBConfig.class)
public class MongoDBTest {
	@Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void insert() {
    	Programmer programmer = new Programmer("xiaoming","26");
    	mongoTemplate.insert(programmer);
    }
    
    
}
class Programmer{
	String name;
	String age;
	Programmer(String name,String age){
		this.name=name;
		this.age=age;
	}
}
