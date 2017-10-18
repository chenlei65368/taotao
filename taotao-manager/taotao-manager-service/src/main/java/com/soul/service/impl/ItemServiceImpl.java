package com.soul.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.IDUtils;
import com.soul.mapper.TbItemDescMapper;
import com.soul.mapper.TbItemMapper;
import com.soul.mapper.TbItemParamItemMapper;
import com.soul.mapper.TbItemParamMapper;
import com.soul.pojo.TbItem;
import com.soul.pojo.TbItemDesc;
import com.soul.pojo.TbItemExample;
import com.soul.pojo.TbItemParam;
import com.soul.pojo.TbItemParamItem;
import com.soul.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Resource
	private TbItemMapper itemMapper;
	
	@Resource
	private TbItemDescMapper itemDescMapper;
	
	@Resource
	private TbItemParamItemMapper itemParamMapper;
	
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

	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
		
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		itemMapper.insert(item);
		TaotaoResult taotaoResult = null;
		taotaoResult = insertItemDesc(itemId, desc);
		if(taotaoResult.getStatus()!=200) {
			throw new Exception("添加商品描述信息失败！");
		}
		taotaoResult = insertItemParam(itemParam,itemId);
		if(taotaoResult.getStatus()!=200) {
			throw new Exception("添加商品参数信息失败！");
		}

		return TaotaoResult.ok();
	}

	private TaotaoResult insertItemParam(String itemParams,Long itemId) {

		TbItemParamItem itemParam = new TbItemParamItem();
		itemParam.setParamData(itemParams);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParam.setItemId(itemId);;
		itemParamMapper.insert(itemParam);
		
		return TaotaoResult.ok();
	}

	private TaotaoResult insertItemDesc(Long itemId,String desc) {
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		itemDescMapper.insert(itemDesc);
		
		
		return TaotaoResult.ok();
	}

}
