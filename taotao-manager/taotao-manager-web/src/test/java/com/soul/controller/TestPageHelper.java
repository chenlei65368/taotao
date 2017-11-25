package com.soul.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soul.mapper.TbItemMapper;
import com.soul.pojo.TbItem;
import com.soul.pojo.TbItemExample;
import com.soul.pojo.TbItemExample.Criteria;

public class TestPageHelper {

	
	public void testPageHelper() {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*");
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andIdGreaterThan((long) 1000);
		PageHelper.startPage(1, 10);
		List<TbItem> list = mapper.selectByExample(example);
		
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println(total);
		
		
	}
}
