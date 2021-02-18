package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    List<EUTreeNode> getContentCatList(Long parentId);
    TaotaoResult insertCatgory(Long parentId, String name) ;
    TaotaoResult updateCatgory(Long id, String name) ;
    TaotaoResult deleteCatgory(Long id) ;

}
