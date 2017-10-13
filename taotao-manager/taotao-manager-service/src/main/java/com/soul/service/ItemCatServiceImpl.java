package com.soul.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soul.common.pojo.EUTreeNode;
import com.soul.mapper.TbItemCatMapper;
import com.soul.pojo.TbItemCat;
import com.soul.pojo.TbItemCatExample;
import com.soul.pojo.TbItemCatExample.Criteria;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EUTreeNode> getCatList(Long parentId) {
		
		List<EUTreeNode> result = new ArrayList<>();
		EUTreeNode euTreeNode;
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andParentIdEqualTo((long) parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		for (TbItemCat tbItemCat : list) {
			euTreeNode =  new EUTreeNode();
			euTreeNode.setId(tbItemCat.getId());
			euTreeNode.setText(tbItemCat.getName());
			euTreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			result.add(euTreeNode);
		}
		return result;
	}

}
