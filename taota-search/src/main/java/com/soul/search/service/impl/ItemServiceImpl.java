package com.soul.search.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.soul.common.pojo.TaotaoResult;
import com.soul.common.utils.ExceptionUtil;
import com.soul.common.utils.JsonUtils;
import com.soul.search.mapper.ItemMapper;
import com.soul.search.pojo.Item;
import com.soul.search.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Resource
	private ItemMapper itemMapper;

	@Resource
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAllItems() {

		try {

			List<Item> itemList = itemMapper.getItemList();

			SolrInputDocument document = null;
			for (Item item : itemList) {

				document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.setField("item_title", item.getTitle());
				document.setField("item_sell_point", item.getSell_point());
				document.setField("item_price", item.getPrice());
				document.setField("item_image", item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				document.setField("item_desc", item.getItem_des());
				// 写入索引库
				solrServer.add(document);

			}
			
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();

	}

	@Override
	public TaotaoResult addDocument(String itemInfo) {
		
		// 写入索引库
		try {
			Item item = JsonUtils.jsonToPojo(itemInfo, Item.class);
			
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_des());
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
