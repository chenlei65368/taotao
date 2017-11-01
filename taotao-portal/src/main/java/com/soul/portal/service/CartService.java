package com.soul.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soul.common.pojo.TaotaoResult;
import com.soul.portal.pojo.CartItem;

public interface CartService {

	TaotaoResult addCartItem(Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response);
	
	List<CartItem> getCartItemList(HttpServletRequest request,HttpServletResponse response);

	TaotaoResult updateCartItemNum(Long itemId, Integer itemNum,HttpServletRequest request, HttpServletResponse response);

	TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
