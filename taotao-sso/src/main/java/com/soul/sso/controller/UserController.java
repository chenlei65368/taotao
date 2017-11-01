package com.soul.sso.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.pojo.TbUser;
import com.soul.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping(value="/check/{param}/{type}",method=RequestMethod.GET)
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
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createUser(TbUser user) {
		
		try {
			TaotaoResult taotaoResult = userService.createUser(user);
			return taotaoResult;
		}catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		try {
			
			TaotaoResult result = userService.userLogin(user.getUsername(), user.getPassword(),request,response);
			return result;
		}catch (Exception e) {
			
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value="/token/{token}",method=RequestMethod.GET)
	@ResponseBody
	public Object getToken(@PathVariable String token,String callback) {
		
		try {
			TaotaoResult result = userService.getToken(token);
			
			if(StringUtils.isNotBlank(callback)) {
				return callback+"("+JsonUtils.objectToJson(result)+");";
			}
			return result;
			
			
		}catch (Exception e) {
			if(StringUtils.isNotBlank(callback)) {
				return callback+"("+JsonUtils.objectToJson( TaotaoResult.build(500, ExceptionUtil.getStackTrace(e)))+");";
			}
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping(value="/logout/{token}",method=RequestMethod.GET)
	@ResponseBody
	public Object logout(@PathVariable String token,String callback) {
		
		try {
			TaotaoResult result = userService.logout(token);
			
			if(StringUtils.isNotBlank(callback)) {
				return callback+"("+JsonUtils.objectToJson(result)+");";
			}
			return result;
			
			
		}catch (Exception e) {
			if(StringUtils.isNotBlank(callback)) {
				return callback+"("+JsonUtils.objectToJson( TaotaoResult.build(500, ExceptionUtil.getStackTrace(e)))+");";
			}
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
}
