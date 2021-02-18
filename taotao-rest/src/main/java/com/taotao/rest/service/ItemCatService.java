package com.taotao.rest.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.pojo.ItemCatResult;


import java.util.List;

public interface ItemCatService {

    ItemCatResult getItemCatList();
    TaotaoResult syncContent(Long parentId);

}
