package com.soul.rest.service;

import java.util.List;

import com.soul.pojo.TbContent;

public interface IContentService {

	
	List<TbContent> getContentList(Long contentID);
}
