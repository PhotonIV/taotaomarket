package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
    EUDataGridResult getContentCatList(Long categoryId, int page, int rows);

    TaotaoResult insertContent(TbContent content);

    TaotaoResult deleteContent(Long ids);

    TaotaoResult updateContent(Long id, Long categoryId, TbContent content);

}
