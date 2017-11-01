package com.soul.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.TaotaoResult;
import com.soul.portal.pojo.CartItem;
import com.soul.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Resource
	private CartService cartService ;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,@RequestParam(defaultValue="1") Integer num,HttpServletRequest request,HttpServletResponse response) {
		
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String toCart(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		List<CartItem> list = cartService.getCartItemList(request, response);
		
		model.addAttribute("cartList", list);
		return "cart";
	}
	
	@RequestMapping(value="/update/num/{itemId}/{itemNum}")
	public String changeItemNum(HttpServletRequest request,HttpServletResponse response,@PathVariable Long itemId,@PathVariable Integer itemNum) {
		
		TaotaoResult result = cartService.updateCartItemNum(itemId,itemNum,request,response);
		return  "redirect:/cart/cart.html";
	}
	
	@RequestMapping(value="/delete/{itemId}")
	public String deleteCartItem(HttpServletRequest request,HttpServletResponse response,@PathVariable Long itemId) {
		
		TaotaoResult result = cartService.deleteCartItem(itemId,request,response);
		return "redirect:/cart/cart.html";
	}
	
	
}
