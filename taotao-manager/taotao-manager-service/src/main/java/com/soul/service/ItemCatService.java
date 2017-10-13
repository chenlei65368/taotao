package com.soul.service;

import java.util.List;

import com.soul.common.pojo.EUTreeNode;

public interface ItemCatService {
	
	public List<EUTreeNode> getCatList(Long parentId);
}
