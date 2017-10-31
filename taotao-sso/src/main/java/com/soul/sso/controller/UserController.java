package com.soul.sso.controller;


import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

		TaotaoResult result = null;
		if (StringUtils.isBlank(param) || type == null) {
			result = TaotaoResult.build(400, "校验内容或类型不能为空");
		}

		if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "校验内容类型超过值域");
		}

		if (null != result) {
			if (null != callback) {
				return callback + "(" + JsonUtils.objectToJson(result) + ")";
			} else {
				return result;
			}
		}
		TaotaoResult taotaoResult = null;
		try {
			taotaoResult = userService.checkData(param, type);

		} catch (Exception e) {

			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (StringUtils.isBlank(callback)) {

			return taotaoResult;

		} else {

			return callback + "(" + JsonUtils.objectToJson(taotaoResult) + ");";
		}

	}
}
