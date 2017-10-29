package com.soul.search.service;

import com.soul.search.pojo.SearchResult;

public interface ISearchService {

	SearchResult search(String queryString,int page,int rows) throws Exception;
}
