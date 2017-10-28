package com.soul.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.rest.service.RedisService;

@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Resource
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public TaotaoResult contentCacheSync(@PathVariable Long contentCid) {
		
		TaotaoResult result = redisService.syncContent(contentCid);
		
		return result;
	}
}
