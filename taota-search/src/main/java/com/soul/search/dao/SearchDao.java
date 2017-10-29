package com.soul.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.soul.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
