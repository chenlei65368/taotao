package com.soul.sso.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.mapper.TbUserMapper;
import com.soul.pojo.TbUser;
import com.soul.pojo.TbUserExample;
import com.soul.pojo.TbUserExample.Criteria;
import com.soul.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private TbUserMapper userMapper;
	
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		//用户名校验
		if( 1 == type) {
			criteria.andUsernameEqualTo(content);
			//手机号校验
		}else if( 2 == type) {
			criteria.andPhoneEqualTo(content);
			//邮箱校验
		}else {
			criteria.andEmailEqualTo(content);
		}
		
		List<TbUser> list = userMapper.selectByExample(tbUserExample);
		if( list==null || list.size()== 0) {
			return TaotaoResult.ok(true);
		}
		
		return TaotaoResult.ok(false);
	}

}
