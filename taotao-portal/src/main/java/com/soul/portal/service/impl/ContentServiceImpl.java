package com.soul.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.HttpClientUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.pojo.TbContent;
import com.soul.portal.service.IContentService;

@Service
@Transactional
public class ContentServiceImpl implements IContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_BIGAD_URL}")
	private String REST_INDEX_BIGAD_URL;
	
	@Override
	public String getContentList() {
		
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_BIGAD_URL);
		
		try {
			
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			
			List<Map> resultList = new ArrayList<>();
			Map map = null;
			for (TbContent tbContent : list) {
				map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("srcB", tbContent.getPic2());
				map.put("height", 240);
				map.put("heightB", 240);
				map.put("width", 670);
				map.put("widthB", 550);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				
				resultList.add(map);
			}
			
			return JsonUtils.objectToJson(resultList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
