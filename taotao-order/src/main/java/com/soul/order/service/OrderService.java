package com.soul.order.service;

import java.util.Date;
import java.util.List;

import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbOrder;
import com.soul.pojo.TbOrderItem;
import com.soul.pojo.TbOrderShipping;

public interface OrderService {

	TaotaoResult createOrder(TbOrder order,List<TbOrderItem> itemList,TbOrderShipping orderShipping);

	TaotaoResult getOrderInfo(Long orderId);

	TaotaoResult getOrderListInfo(Long userID, Integer page, Integer count);

	TaotaoResult changeOrderStatus(String orderId, Integer status, Date paymentTime);
}
