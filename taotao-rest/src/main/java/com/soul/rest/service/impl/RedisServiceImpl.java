package com.soul.rest.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.rest.dao.JedisClient;
import com.soul.rest.service.RedisService;

@Service
@Transactional
public class RedisServiceImpl implements RedisService {

	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Resource
	private JedisClient jedisClient;
	
	@Override
	public TaotaoResult syncContent(Long contentId) {
		
		try {
			long hdel = jedisClient.hdel(INDEX_CONTENT_REDIS_KEY,contentId+"");
		}catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return TaotaoResult.ok();
	}

}
