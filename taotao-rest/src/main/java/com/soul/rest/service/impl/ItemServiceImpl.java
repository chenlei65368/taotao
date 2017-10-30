package com.soul.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.JsonUtils;
import com.soul.mapper.TbItemMapper;
import com.soul.mapper.TbItemParamItemMapper;
import com.soul.pojo.TbItem;
import com.soul.pojo.TbItemParamItem;
import com.soul.pojo.TbItemParamItemExample;
import com.soul.pojo.TbItemParamItemExample.Criteria;
import com.soul.rest.dao.JedisClient;
import com.soul.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Resource
	private TbItemMapper itemMapper;

	@Resource
	private JedisClient jedisClient;

	@Resource
	private TbItemParamItemMapper paramItemMapper;

	@Override
	public TaotaoResult getItemBaseInfo(Long itemId) {

		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":base:" + itemId);
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaotaoResult.ok(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

		try {

			jedisClient.set(REDIS_ITEM_KEY + ":base:" + itemId, JsonUtils.objectToJson(tbItem));
			jedisClient.expire(REDIS_ITEM_KEY + ":base:" + itemId, REDIS_ITEM_EXPIRE);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return TaotaoResult.ok(tbItem);
	}

	@Override
	public TaotaoResult getItemParamInfo(Long itemId) {

		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":param:" + itemId);
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaotaoResult.ok(paramItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemParamItemExample itemExample = new TbItemParamItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		List<TbItemParamItem> list = paramItemMapper.selectByExampleWithBLOBs(itemExample);
		if (list != null && list.size() > 0) {

			TbItemParamItem paramItem = list.get(0);
			try {

				jedisClient.set(REDIS_ITEM_KEY + ":param:" + itemId, JsonUtils.objectToJson(paramItem));
				jedisClient.expire(REDIS_ITEM_KEY + ":param:" + itemId, REDIS_ITEM_EXPIRE);
			} catch (Exception e) {

				e.printStackTrace();
			}

			return TaotaoResult.ok(paramItem);
		}

		return TaotaoResult.build(400, "无此商品规格");

	}

}
