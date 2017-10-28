package com.soul.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.utils.JsonUtils;
import com.soul.mapper.TbContentMapper;
import com.soul.pojo.TbContent;
import com.soul.pojo.TbContentExample;
import com.soul.pojo.TbContentExample.Criteria;
import com.soul.rest.dao.JedisClient;
import com.soul.rest.service.IContentService;

@Service
public class ContentServiceImpl implements IContentService {

	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Resource
	private TbContentMapper contentMapper;
	
	@Resource
	private JedisClient jedisClient;
	
	@Override
	public List<TbContent> getContentList(Long contentID) {
		
		//获取缓存
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentID+"");
			if(!StringUtils.isBlank(result)) {
				//把字符串转成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(contentID);
		
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		
		//加入缓存
		try {			
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentID+"", cacheString);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
