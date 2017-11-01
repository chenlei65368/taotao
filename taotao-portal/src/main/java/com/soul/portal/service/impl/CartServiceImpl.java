package com.soul.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.CookieUtils;
import com.soul.common.utils.HttpClientUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.pojo.TbItem;
import com.soul.portal.pojo.CartItem;
import com.soul.portal.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Override
	public TaotaoResult addCartItem(Long itemId, Integer num,HttpServletRequest request,HttpServletResponse response) {
		
		CartItem cartItem = null;
		boolean flag = true;
		List<CartItem> itemList = getCartItemList(request);
		if( itemList != null) {
			
			for (CartItem cItem : itemList) {
				
				if(cItem.getId() == itemId) {
					
					cItem.setNum(cItem.getNum()+num);
					flag = false;
					break;
				}
			}
		}else {
			itemList = new ArrayList<>();
		}
		
		if(flag) {
			cartItem = new CartItem();
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			
			if(result.getStatus() == 200) {
				
				TbItem item = (TbItem) result.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage() == null? "":item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			itemList.add(cartItem);
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),true);
		
		return TaotaoResult.ok();
	}

	private List<CartItem> getCartItemList(HttpServletRequest request) {
		
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART","utf-8");
		
		List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
		
		return list;
		
	}

	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		return getCartItemList(request);
	}

	@Override
	public TaotaoResult updateCartItemNum(Long itemId, Integer itemNum,HttpServletRequest request, HttpServletResponse response) {
		
		List<CartItem> itemList = getCartItemList(request);
		if( itemList != null) {
			
			for (CartItem cItem : itemList) {
				
				if(cItem.getId() == itemId) {
					
					cItem.setNum(itemNum);
					break;
				}
			}
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),true);
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		
		List<CartItem> itemList = getCartItemList(request);
		if( itemList != null) {
			
			//TODO 进不去if
			for (CartItem cItem : itemList) {
				
				if( itemId == cItem.getId() ) {
					itemList.remove(cItem);
					break;
				}
			}
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),true);
		
		return TaotaoResult.ok();
	}

}
