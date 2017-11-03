package com.soul.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soul.pojo.TbUser;
import com.soul.portal.pojo.CartItem;
import com.soul.portal.pojo.Order;
import com.soul.portal.service.CartService;
import com.soul.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	private CartService cartService;
	
	@Resource
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String toOrder(HttpServletRequest request,HttpServletResponse response,Model model) {
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", itemList);
		
		return "order-cart";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String createOrder(Order order,Model model,HttpServletRequest request) {
		
		try {
			TbUser user = (TbUser) request.getAttribute("user");
			order.setBuyerNick(user.getUsername());
			order.setUserId(user.getId());
			String result = orderService.createOrder(order);
			model.addAttribute("orderId", result);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错！");
			return "error/exception";
		}
		
	}
}
