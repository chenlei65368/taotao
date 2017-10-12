package com.soul.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soul.common.pojo.EUDatagridResult;
import com.soul.mapper.TbItemMapper;
import com.soul.pojo.TbItem;
import com.soul.pojo.TbItemExample;

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
	public EUDatagridResult getAllByLimit(int page, int size) {
		
		TbItemExample example = new TbItemExample();
		
		PageHelper.startPage(page, size);
		List<TbItem> list = itemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EUDatagridResult result = new EUDatagridResult();
		
		result.setRows(list);
		result.setTotal((int) total);
		return result;
	}

}
