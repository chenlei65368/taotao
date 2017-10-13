package com.soul.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.EUTreeNode;
import com.soul.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Resource
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	private List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		List<EUTreeNode> catList = itemCatService.getCatList(parentId);
		
		return catList;
	}
}
