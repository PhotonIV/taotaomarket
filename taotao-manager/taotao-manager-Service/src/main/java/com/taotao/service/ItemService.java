package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(Long itemId);

    EUDataGridResult getItemList(int page, int rows);

    TaotaoResult createItem(TbItem item, String desc, String itemParam);

    TaotaoResult updateItem(TbItem item, String desc, String itemParam);

    TaotaoResult deleteItem(Long itemId);

    TaotaoResult showItemQuery(Long itemId);

    TaotaoResult showItemDesc(Long itemId);

    String getItemParamHtml(Long itemId);
}

















