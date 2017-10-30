package com.soul.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.rest.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

	@Resource
	private ItemDescService itemDescService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		
		TaotaoResult result = itemDescService.getItemDesc(itemId);
		return result;
	}
}
