package com.soul.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.common.pojo.EUTreeNode;
import com.soul.common.pojo.TaotaoResult;
import com.soul.mapper.TbContentCategoryMapper;
import com.soul.pojo.TbContentCategory;
import com.soul.pojo.TbContentCategoryExample;
import com.soul.pojo.TbContentCategoryExample.Criteria;
import com.soul.service.IItemContentCategoryService;



@Service
public class ItemContentCategoryServiceImpl implements IItemContentCategoryService {

	
	@Resource
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getContentList(Long parentId) {
		List<EUTreeNode> result = new ArrayList<>();
		EUTreeNode euTreeNode;
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andParentIdEqualTo((long) parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		for (TbContentCategory tbContentCategory : list) {
			euTreeNode =  new EUTreeNode();
			euTreeNode.setId(tbContentCategory.getId());
			euTreeNode.setText(tbContentCategory.getName());
			euTreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(euTreeNode);
		}
		return result;
	}

	@Override
	public TaotaoResult createContentCategoryService(String name, Long parentId) {
		
		TaotaoResult result = qulifyRepeatData(name,parentId);
		if( result.getStatus()!=200)
			return result;
		TbContentCategory category = new TbContentCategory();
		
		category.setCreated(new Date());
		category.setUpdated(new Date());
		category.setName(name);
		category.setParentId(parentId);
		category.setIsParent(false);
		category.setSortOrder(1);
		category.setStatus(1);
		int insert = contentCategoryMapper.insert(category);
		
		result = modifyParentStatus(parentId,category);
		
		return result;
	}

	private TaotaoResult modifyParentStatus(Long parentId, TbContentCategory category2) {
		TbContentCategoryExample categoryExample  = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(categoryExample);
		TbContentCategory category = null;
		if(list!=null && list.size() > 0) {
			 category = list.get(0);
			 if(category.getIsParent()==false) {
				 category.setIsParent(true);
			 }
			 contentCategoryMapper.updateByPrimaryKey(category);
			 return TaotaoResult.ok(category);
		}
		return TaotaoResult.build(400, "不存在该类目!!!");
	}

	private TaotaoResult qulifyRepeatData(String name, Long parentId) {
		TbContentCategoryExample categoryExample  = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andNameEqualTo(name);
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(categoryExample);
		
		if(list!=null && list.size() > 0 ) {
			return TaotaoResult.build(400, "同类目下不能存在同名分类!!!");
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContentCategoryService(Long id) {
		TbContentCategoryExample categoryExample  = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andIdEqualTo(id);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(categoryExample);
		
		if(list==null || list.size() <0 ) {
			return TaotaoResult.build(400, "不存在该节点，无法删除!!");
		}
		TbContentCategory category = list.get(0);
		if(category.getIsParent()) {
			deleteSonNode(category.getId());
		}
		contentCategoryMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

	private void deleteSonNode(Long id) {
		
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(categoryExample);
		for (TbContentCategory tbContentCategory : list) {
			contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
		}
	}

}
