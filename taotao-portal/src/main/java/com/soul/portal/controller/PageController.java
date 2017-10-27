package com.soul.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soul.portal.service.IContentService;

@Controller
public class PageController {
	
	@Resource
	private IContentService contentService;
	
	@RequestMapping("/index")
	public String toIndex(Model model) {
		
		String contentList = contentService.getContentList();
		model.addAttribute("ad1", contentList);
		return "index";
	}
}
