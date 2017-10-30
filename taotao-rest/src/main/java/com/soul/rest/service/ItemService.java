package com.soul.rest.service;

import com.soul.common.pojo.TaotaoResult;

public interface ItemService {

	TaotaoResult getItemBaseInfo(Long itemId);
	TaotaoResult getItemParamInfo(Long itemId);
}
