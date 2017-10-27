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
import com.soul.mapper.TbContentMapper;
import com.soul.pojo.TbContent;
import com.soul.pojo.TbContentExample;
import com.soul.pojo.TbContentExample.Criteria;
import com.soul.service.IItemContentService;

@Service
@Transactional
public class ItemContentServiceImpl implements IItemContentService {

	@Resource
	private TbContentMapper contentMapper;
	
	@Override
	public EUDatagridResult queryByLimit(int page, int rows,Long categoryId) {
		
		
		EUDatagridResult datagridResult = new EUDatagridResult();
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		PageHelper.startPage(page, rows);
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(contentExample);
		
		PageInfo<TbContent> info = new PageInfo<>(list);
		long total = info.getTotal();
		
		datagridResult.setRows(list);
		datagridResult.setTotal((int) total);
		
		return datagridResult;
	}

	@Override
	public TaotaoResult saveContent(TbContent content) {
		
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

}
