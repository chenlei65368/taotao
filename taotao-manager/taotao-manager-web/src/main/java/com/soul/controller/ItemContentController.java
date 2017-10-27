package com.soul.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbContent;
import com.soul.service.IItemContentService;

@Controller
@RequestMapping("/content")
public class ItemContentController {
	
	@Resource
	private IItemContentService contentService;
	
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDatagridResult contentList(int page,int rows,Long categoryId) {
		
		return contentService.queryByLimit(page,rows,categoryId);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content) {
		
		return contentService.saveContent(content);
	}
}
