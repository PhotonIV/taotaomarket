package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
    TaotaoResult importItem() throws Exception;
    TaotaoResult importItemNew() throws Exception;
    TaotaoResult deleteItem(Long id) throws Exception;
    TaotaoResult updateItem(Long id) throws Exception;
    TaotaoResult getById(Long id)throws Exception;
}
