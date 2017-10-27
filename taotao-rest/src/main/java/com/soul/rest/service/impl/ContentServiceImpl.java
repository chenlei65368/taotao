package com.soul.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.mapper.TbContentMapper;
import com.soul.pojo.TbContent;
import com.soul.pojo.TbContentExample;
import com.soul.pojo.TbContentExample.Criteria;
import com.soul.rest.service.IContentService;

@Service
public class ContentServiceImpl implements IContentService {

	
	@Resource
	private TbContentMapper contentMapper;
	
	@Override
	public List<TbContent> getContentList(Long contentID) {
		
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(contentID);
		
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		return list;
	}

}
