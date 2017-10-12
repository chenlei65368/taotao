package com.soul.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.pojo.TbItem;


public interface IItemService {
	
	public TbItem getItem(long itemId);
	public EUDatagridResult getAllByLimit(int page,int size);
}
