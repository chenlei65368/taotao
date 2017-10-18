package com.soul.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soul.common.pojo.EUDatagridResult;
import com.soul.common.pojo.TaotaoResult;
import com.soul.pojo.TbItemParam;
import com.soul.service.IItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	
	@Resource
	private IItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		
		TaotaoResult taotaoResult = itemParamService.getItemParamBycid(itemCatId);
		
		return taotaoResult;
	}
	
	@RequestMapping(value="/save/{cid}",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		
		TaotaoResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}
	
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDatagridResult list(int page,int rows) {
		
		EUDatagridResult result = itemParamService.getAllByLimit(page, rows);
		
		return result;
	}
}
