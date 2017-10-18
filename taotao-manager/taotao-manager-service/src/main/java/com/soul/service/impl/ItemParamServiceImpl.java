package com.soul.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.mapper.TbItemParamMapper;
import com.soul.pojo.TbItemParam;
import com.soul.pojo.TbItemParamExample;
import com.soul.pojo.TbItemParamExample.Criteria;
import com.soul.service.IItemParamService;

@Service
@Transactional
public class ItemParamServiceImpl implements IItemParamService {

	@Resource
	private TbItemParamMapper itemParamMapper;
	

	@Override
	public TaotaoResult getItemParamBycid(Long cid) {
		
		TbItemParamExample itemParamExample = new TbItemParamExample();
		
		
		Criteria criteria = itemParamExample.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
		
		if(list != null && list.size()>0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}


	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		int i = itemParamMapper.insert(itemParam);
		
		return TaotaoResult.ok();
	}


	@Override
	public EUDatagridResult getAllByLimit(int page, int rows) {
		
		TbItemParamExample example = new TbItemParamExample();
		
		PageHelper.startPage(page,rows);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		
		long total = pageInfo.getTotal();
		EUDatagridResult result = new EUDatagridResult();
		result.setRows(list);
		result.setTotal((int) total);
		
		return result;
	}

}
