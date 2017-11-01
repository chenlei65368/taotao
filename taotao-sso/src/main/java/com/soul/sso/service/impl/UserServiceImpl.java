package com.soul.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.CookieUtils;
import com.soul.common.utils.JsonUtils;
import com.soul.mapper.TbUserMapper;
import com.soul.pojo.TbUser;
import com.soul.pojo.TbUserExample;
import com.soul.pojo.TbUserExample.Criteria;
import com.soul.sso.dao.JedisClient;
import com.soul.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	@Resource
	private TbUserMapper userMapper;
	@Resource
	private JedisClient jedisClient;

	@Override
	public TaotaoResult checkData(String content, Integer type) {

		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		// 用户名校验
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
			// 手机号校验
		} else if (2 == type) {
			criteria.andPhoneEqualTo(content);
			// 邮箱校验
		} else {
			criteria.andEmailEqualTo(content);
		}

		List<TbUser> list = userMapper.selectByExample(tbUserExample);
		if (list == null || list.size() == 0) {
			return TaotaoResult.ok(true);
		}

		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult createUser(TbUser tbUser) {
		TaotaoResult checkData = null;
		boolean flag = false;
		checkData = checkData(tbUser.getUsername(), 1);
		flag = (boolean) checkData.getData();

		if (!flag) {
			return TaotaoResult.build(400, "注册失败. 请校验数据后请再提交数据.");
		}
		if (tbUser.getEmail() != null) {

			checkData = checkData(tbUser.getPhone(), 2);
			flag = (boolean) checkData.getData();

			if (!flag) {
				return TaotaoResult.build(400, "注册失败. 请校验数据后请再提交数据.");
			}
		}

		if (tbUser.getEmail() != null) {

			checkData = checkData(tbUser.getEmail(), 3);
			flag = (boolean) checkData.getData();

			if (!flag) {
				return TaotaoResult.build(400, "注册失败. 请校验数据后请再提交数据.");
			}
		}

		tbUser.setUpdated(new Date());
		tbUser.setCreated(new Date());
		String newPassword = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(newPassword);
		userMapper.insert(tbUser);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);

		if (null == list || 0 == list.size()) {
			return TaotaoResult.build(400, "用户名或密码错误!");
		}
		TbUser user = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误!");
		}

		// 生产token
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		user.setPassword(null);
		// 把用户信息写入Redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		//向客户端添加cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getToken(String token) {

		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "session已过期！");
		}

		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	@Override
	public TaotaoResult logout(String token) {

		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, 1);

		return TaotaoResult.ok();
	}

}
