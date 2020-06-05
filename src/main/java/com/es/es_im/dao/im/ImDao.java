package com.es.es_im.dao.im;

import java.util.List;
import java.util.Map;

import com.es.es_im.dao.MyBatisRepository;

@MyBatisRepository
public interface ImDao {

	int saveMessage(Map<String,Object> params);

	int saveGroupMessage(Map<String, Object> params);
	
	List<Map<String, Object>> getGroupAccountList(Map<String, Object> params);
}
