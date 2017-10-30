package com.soul.portal.service;

import com.soul.portal.pojo.SearchResult;

public interface IPortalSearchService {

	SearchResult search(String queryString , int page);
}
