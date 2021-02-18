package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {
    public List<TbContent> getContentList(Long cid);
    public TaotaoResult syncContent(Long cid);

}
