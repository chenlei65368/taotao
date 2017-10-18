package com.soul.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soul.service.IItemParamItemService;

@Controller
public class ItemParamItemController {

	
	@Resource
	private IItemParamItemService itemParamItemService;
	
	@RequestMapping("/showitem/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model) {
		
		String paramByItemId = itemParamItemService.getItemParamByItemId(itemId);
		
		model.addAttribute("itemParam", paramByItemId);
		
		return "item";
	}
}
