package com.soul.service;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbItemParam;

public interface IItemParamService {
	
	TaotaoResult getItemParamBycid(Long cid);

	TaotaoResult insertItemParam(TbItemParam itemParam);

	EUDatagridResult getAllByLimit(int page, int rows);
}
