package com.soul.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soul.common.utils.JsonUtils;
import com.soul.service.IPicService;

@Controller
@RequestMapping("/pic")
public class PictureController {
	
	@Resource
	private IPicService picService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		
		Map result = picService.uploadPicture(uploadFile);
		
		String json = JsonUtils.objectToJson(result);
		
		return json;
	}
}
