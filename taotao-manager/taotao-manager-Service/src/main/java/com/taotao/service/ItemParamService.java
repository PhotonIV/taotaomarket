package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
    TaotaoResult getItemParamByCid(Long cid);

    TaotaoResult insertParam(Long cid, String paramData);

    EUDataGridResult getItemParamList(int page, int rows);
}
