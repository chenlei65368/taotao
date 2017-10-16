package com.soul.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbItem;
import com.soul.service.IItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	private IItemService itemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		
		return itemService.getItem(itemId);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDatagridResult list(int page,int rows) {
		
		return itemService.getAllByLimit(page, rows);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult itemSave(TbItem item) {
		
		
		return itemService.createItem(item);
	}
}
