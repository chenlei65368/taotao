package com.soul.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.HttpClientUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.portal.pojo.Order;
import com.soul.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	
	@Override
	public String createOrder(Order order) {
		
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		
		TaotaoResult taotaoResult = TaotaoResult.format(json);
		
		if( taotaoResult.getStatus() == 200) {
			Integer orderId =  (Integer) taotaoResult.getData();
			return orderId.toString();
		}
		
		return "";
	}

}
