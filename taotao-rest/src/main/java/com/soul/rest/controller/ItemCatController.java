package com.soul.rest.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.utils.JsonUtils;
import com.soul.rest.pojo.CatResult;
import com.soul.rest.service.ItemCatService;

@Controller
@RequestMapping("/itemcat")
public class ItemCatController {

	@Resource
	private ItemCatService itemCatService;

	@RequestMapping(value="/all",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getCatList(String callback) {

		CatResult catResult = itemCatService.getItemCatList();

		String json = JsonUtils.objectToJson(catResult);

		String result = callback + "(" + json + ")";

		return result;
	}
}
