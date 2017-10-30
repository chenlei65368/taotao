package com.soul.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.HttpClientUtil;
import com.soul.portal.pojo.SearchResult;
import com.soul.portal.service.IPortalSearchService;

@Service
public class PortalServiceImpl implements IPortalSearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
		
		try {
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
			if( taotaoResult.getStatus() == 200) {
				SearchResult result = (SearchResult) taotaoResult.getData();
				
				return result;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
