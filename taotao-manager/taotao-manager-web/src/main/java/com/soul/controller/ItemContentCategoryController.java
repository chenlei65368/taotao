package com.soul.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.EUTreeNode;
import com.soul.common.pojo.TaotaoResult;
import com.soul.service.IItemContentCategoryService;

@Controller
@RequestMapping("/content")
public class ItemContentCategoryController {
	
	
	@Resource
	private IItemContentCategoryService itemContentCategoryService;
	
	@RequestMapping("/category/list")
	@ResponseBody
	private List<EUTreeNode> getContentList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		List<EUTreeNode> catList = itemContentCategoryService.getContentList(parentId);
		
		return catList;
	}
	
	@RequestMapping("/category/create")
	@ResponseBody
	public TaotaoResult createContent(String name,Long parentId) {
		
		TaotaoResult taotaoResult =  itemContentCategoryService.createContentCategoryService(name,parentId);
		return taotaoResult;
	}
	
}
