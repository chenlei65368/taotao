package com.soul.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.order.pojo.Order;
import com.soul.order.service.OrderService;

@Controller
public class OrderController {

	@Resource
	private OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createOrder(@RequestBody Order order) {

		try {

			TaotaoResult taotaoResult = orderService.createOrder(order, order.getOrderItems(),
					order.getOrderShipping());
			return taotaoResult;
		} catch (Exception e) {

			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult changeOrderStatus(@RequestBody Order order) {

		try {

			TaotaoResult taotaoResult = orderService.changeOrderStatus(order.getOrderId(), order.getStatus(),
					order.getPaymentTime());
			return taotaoResult;
		} catch (Exception e) {

			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/info/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getOrderInfo(@PathVariable Long orderId) {
		try {

			TaotaoResult result = orderService.getOrderInfo(orderId);

			return result;
		} catch (Exception e) {

			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

	}

	@RequestMapping(value = "/list/{userID}/{page}/{count}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getOrderListInfo(@PathVariable Long userID,
			@PathVariable @RequestParam(defaultValue = "1") Integer page,
			@PathVariable @RequestParam(defaultValue = "20") Integer count) {
		try {

			TaotaoResult result = orderService.getOrderListInfo(userID, page, count);

			return result;
		} catch (Exception e) {

			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

	}
}
