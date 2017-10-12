package com.soul.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.mapper.TbItemMapper;
import com.soul.pojo.TbItem;
import com.sun.tools.javac.util.List;

@Service
public class ItemServiceImpl implements IItemService {

	@Resource
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItem(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		return item;
	}

	@Override
	public List<TbItem> getAllByLimit(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
