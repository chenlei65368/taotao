package com.soul.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.mapper.TbItemCatMapper;
import com.soul.pojo.TbItemCat;
import com.soul.pojo.TbItemCatExample;
import com.soul.pojo.TbItemCatExample.Criteria;
import com.soul.rest.pojo.CatNode;
import com.soul.rest.pojo.CatResult;
import com.soul.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {

		CatResult catResult = new CatResult();
		catResult.setData(getCatList((long) 0));

		return catResult;
	}

	private List<?> getCatList(Long parentId) {

		TbItemCatExample catExample = new TbItemCatExample();
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbItemCat> list = itemCatMapper.selectByExample(catExample);

		List resultList = new ArrayList<>();

		CatNode catNode = null;
		int count = 0;
		for (TbItemCat itemCat : list) {
			
			if(parentId == 0 && count>13) {
				break;
			}
			if (itemCat.getIsParent()) {

				catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				} else {
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/" + itemCat.getId() +".html");
				catNode.setItem(getCatList(itemCat.getId()));

				resultList.add(catNode);
				count++;
				
			} else {

				resultList.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
			}

		}

		return resultList;
	}

}
