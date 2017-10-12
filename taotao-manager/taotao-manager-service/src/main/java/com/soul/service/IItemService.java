package com.soul.service;

import org.springframework.stereotype.Service;

import com.soul.pojo.TbItem;
import com.sun.tools.javac.util.List;

@Service
public interface IItemService {
	
	public TbItem getItem(long itemId);
	public List<TbItem> getAllByLimit(int page,int size);
}
