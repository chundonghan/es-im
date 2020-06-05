package com.es.es_im.common.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import  com.es.es_im.dao.redis.RedisDao; 
@Component
public class LocalInit implements ApplicationListener<ContextRefreshedEvent>
{
    @Autowired
    private RedisDao redisDao;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent  event)
    {
        if(event.getApplicationContext().getParent() == null&& event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){//root application context 没有parent，他就是老大.  
           //初始化参数 操作数据库初始化信息
        	
       } 
       
    }

}
