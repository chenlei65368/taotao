package com.soul.order.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.soul.common.pojo.TaotaoResult;
import com.soul.mapper.TbOrderItemMapper;
import com.soul.mapper.TbOrderMapper;
import com.soul.mapper.TbOrderShippingMapper;
import com.soul.order.dao.JedisClient;
import com.soul.order.pojo.Order;
import com.soul.pojo.TbOrder;
import com.soul.pojo.TbOrderExample;
import com.soul.pojo.TbOrderItem;
import com.soul.pojo.TbOrderItemExample;
import com.soul.pojo.TbOrderItemExample.Criteria;
import com.soul.pojo.TbOrderShipping;
import com.soul.pojo.TbOrderShippingExample;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;

	@Resource
	private JedisClient jedisClient;
	@Resource
	private TbOrderMapper orderMapper;
	@Resource
	private TbOrderItemMapper orderItemMapper;
	@Resource
	private TbOrderShippingMapper shippingMapper;

	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {

		String string = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}

		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		order.setOrderId(orderId + "");
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		// '状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
		order.setStatus(1);
		// '买家是否已经评价'
		order.setBuyerRate(0);
		orderMapper.insert(order);

		for (TbOrderItem tbOrderItem : itemList) {

			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");

			orderItemMapper.insert(tbOrderItem);
		}
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		shippingMapper.insert(orderShipping);

		return TaotaoResult.ok(orderId);
	}

	@Override
	public TaotaoResult getOrderInfo(Long orderId) {

		Order order = new Order();
		TbOrder tbOrder = orderMapper.selectByPrimaryKey(orderId + "");
		copyOrder(tbOrder, order);

		TbOrderItemExample orderItemExample = new TbOrderItemExample();
		Criteria criteria = orderItemExample.createCriteria();
		criteria.andOrderIdEqualTo(orderId + "");
		List<TbOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
		order.setOrderItems(orderItemList);

		TbOrderShippingExample orderShippingExample = new TbOrderShippingExample();
		com.soul.pojo.TbOrderShippingExample.Criteria criteria2 = orderShippingExample.createCriteria();
		criteria2.andOrderIdEqualTo(orderId + "");
		List<TbOrderShipping> shipping = shippingMapper.selectByExample(orderShippingExample);
		if (shipping != null && shipping.size() > 0)
			order.setOrderShipping(shipping.get(0));

		return TaotaoResult.ok(order);
	}

	private void copyOrder(TbOrder tbOrder, Order order) {

		order.setBuyerMessage(tbOrder.getBuyerMessage());
		order.setBuyerNick(tbOrder.getBuyerNick());
		order.setBuyerRate(tbOrder.getBuyerRate());
		order.setCloseTime(tbOrder.getCreateTime());
		order.setConsignTime(tbOrder.getConsignTime());
		order.setCreateTime(tbOrder.getCreateTime());
		order.setEndTime(tbOrder.getEndTime());
		order.setOrderId(tbOrder.getOrderId());
		order.setPayment(tbOrder.getPayment());
		order.setPaymentTime(tbOrder.getPaymentTime());
		order.setPaymentType(tbOrder.getPaymentType());
		order.setPostFee(tbOrder.getPostFee());
		order.setShippingCode(tbOrder.getShippingCode());
		order.setShippingName(tbOrder.getShippingName());
		order.setStatus(tbOrder.getStatus());
		order.setUpdateTime(tbOrder.getUpdateTime());
		order.setUserId(tbOrder.getUserId());

	}

	@Override
	public TaotaoResult getOrderListInfo(Long userID, Integer page, Integer count) {

		TbOrderExample example = new TbOrderExample();
		com.soul.pojo.TbOrderExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userID);
		PageHelper.startPage(page, count);

		List<TbOrder> list = orderMapper.selectByExample(example);

		return TaotaoResult.ok(list);
	}

	@Override
	public TaotaoResult changeOrderStatus(String orderId, Integer status, Date paymentTime) {

		TbOrderExample example = new TbOrderExample();
		com.soul.pojo.TbOrderExample.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<TbOrder> list = orderMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			TbOrder order = list.get(0);
			order.setStatus(status);
			order.setPaymentTime(paymentTime);

			orderMapper.updateByPrimaryKey(order);
			return TaotaoResult.ok();
		}

		return TaotaoResult.build(400, "请求的订单不存在！");
	}

}
