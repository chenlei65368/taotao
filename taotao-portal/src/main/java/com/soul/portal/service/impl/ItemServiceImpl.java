package com.soul.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.HttpClientUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.pojo.TbItemDesc;
import com.soul.pojo.TbItemParamItem;
import com.soul.portal.pojo.ItemInfo;
import com.soul.portal.service.ItemService;


@Service
public class ItemServiceImpl implements ItemService {

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	
	@Override
	public ItemInfo getItemById(Long itemId) {
		
		try {
			
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(taotaoResult.getStatus()==200) {
					ItemInfo item = (ItemInfo) taotaoResult.getData();
					return item;
				}
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getItemDescById(Long itemId) {
		
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
			if(taotaoResult != null && taotaoResult.getStatus()==200) {
				TbItemDesc desc = (TbItemDesc) taotaoResult.getData();
				
				String itemDesc = desc.getItemDesc();
				return itemDesc;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public String getItemParam(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
			if(taotaoResult.getStatus()==200) {
				TbItemParamItem paramItem = (TbItemParamItem) taotaoResult.getData();
				
				String paramData = paramItem.getParamData();
				//生成html
				// 把规格参数json数据转换成java对象
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
				//返回html片段
				return sb.toString();

			}
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		return "";
	}

}
