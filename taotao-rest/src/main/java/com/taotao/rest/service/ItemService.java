package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {
    TbItem getById(Long itemId);
    TbItemDesc getDescById(Long itemId);
    TbItemParamItem getItemParamById(Long itemId);
}
