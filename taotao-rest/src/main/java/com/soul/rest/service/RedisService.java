package com.soul.rest.service;

import com.soul.common.pojo.TaotaoResult;

public interface RedisService {
	
	TaotaoResult syncContent(Long contentId);
}
