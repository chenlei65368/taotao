package com.soul.rest.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.JsonUtils;
import com.soul.mapper.TbItemDescMapper;
import com.soul.pojo.TbItem;
import com.soul.pojo.TbItemDesc;
import com.soul.rest.dao.JedisClient;
import com.soul.rest.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Resource
	private TbItemDescMapper itemDescMapper;
	@Resource
	private JedisClient jedisClient;
	
	@Override
	public TaotaoResult getItemDesc(Long itemId) {
		
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":desc:" + itemId );
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			
			jedisClient.set(REDIS_ITEM_KEY+":desc:"+itemId, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(REDIS_ITEM_KEY+":desc:"+itemId,REDIS_ITEM_EXPIRE);
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return TaotaoResult.ok(itemDesc);
	}

}
