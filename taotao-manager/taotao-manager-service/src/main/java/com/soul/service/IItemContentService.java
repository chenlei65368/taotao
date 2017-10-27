package com.soul.service;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbContent;

public interface IItemContentService {

	EUDatagridResult queryByLimit(int page, int rows, Long categoryId);

	TaotaoResult saveContent(TbContent content);

}
