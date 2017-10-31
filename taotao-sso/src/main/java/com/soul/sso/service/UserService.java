package com.soul.sso.service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbUser;

public interface UserService {
	
	TaotaoResult checkData(String content,Integer type);
	TaotaoResult createUser(TbUser tbUser);
	TaotaoResult userLogin(String username,String password);
	TaotaoResult getToken(String token);
	TaotaoResult logout(String token);
}
