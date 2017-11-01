package com.soul.portal.service;

import com.soul.pojo.TbUser;

public interface UserService {

	TbUser getUserByToken(String token);
}
