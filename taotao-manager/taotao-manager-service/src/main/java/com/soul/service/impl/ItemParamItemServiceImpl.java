package com.soul.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soul.common.utils.JsonUtils;
import com.soul.mapper.TbItemParamItemMapper;
import com.soul.pojo.TbItemParamExample;
import com.soul.pojo.TbItemParamItem;
import com.soul.pojo.TbItemParamItemExample;
import com.soul.pojo.TbItemParamItemExample.Criteria;
import com.soul.service.IItemParamItemService;

@Service
public class ItemParamItemServiceImpl  implements IItemParamItemService{

	@Resource
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public String getItemParamByItemId(Long itemId) {

		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list == null && list.size()==0) {
			
			return "";
		}
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");

		
		return sb.toString();
	}

}
