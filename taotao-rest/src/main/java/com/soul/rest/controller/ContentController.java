package com.soul.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.pojo.TbContent;
import com.soul.rest.service.IContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Resource
	private IContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
		
		try {
			
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return TaotaoResult.ok(list);
		}catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "返回内容列表失败！！"+ExceptionUtil.getStackTrace(e));
		}
		
	}
}
