package com.soul.rest.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrJTest {

	public void addDocument() throws Exception {
		
		SolrServer solrServer = new HttpSolrServer("http://192.168.152.155:8080/solr");
		
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		document.addField("item_price", 123456);
		solrServer.add(document);
		solrServer.commit();
	}
	
	public void deleteDocument() throws Exception {
		
		SolrServer solrServer = new HttpSolrServer("http://192.168.152.155:8080/solr");
		
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
}
