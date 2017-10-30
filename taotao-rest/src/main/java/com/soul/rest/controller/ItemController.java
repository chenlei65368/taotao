package com.soul.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Resource
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
		
		TaotaoResult taotaoResult = itemService.getItemBaseInfo(itemId);
		
		return taotaoResult;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamInfo(@PathVariable Long itemId) {
		
		TaotaoResult taotaoResult = itemService.getItemParamInfo(itemId);
		
		return taotaoResult;
	}
}
