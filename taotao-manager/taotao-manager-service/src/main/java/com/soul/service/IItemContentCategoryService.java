package com.soul.service;

import java.util.List;

import com.soul.common.pojo.EUTreeNode;
import com.soul.common.pojo.TaotaoResult;

public interface IItemContentCategoryService {

	List<EUTreeNode> getContentList(Long parentId);

	TaotaoResult createContentCategoryService(String name, Long parentId);

}
