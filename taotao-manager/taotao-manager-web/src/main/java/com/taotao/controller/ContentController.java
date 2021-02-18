package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private TbContentMapper contentMapper;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentCatList(Long categoryId, int page, int rows) {
        EUDataGridResult result = contentService.getContentCatList(categoryId,page,rows);
        return result;
    }
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content){
        TaotaoResult result=contentService.insertContent(content);
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(Long ids){
        TaotaoResult result=contentService.deleteContent(ids);
        Long categoryId =(Long) result.getData();
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+categoryId);
        return result;
    }
    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult updateContent(Long id,Long categroyId,TbContent content){
        TaotaoResult result=contentService.updateContent(id,categroyId,content);
        Long categoryId =(Long) result.getData();
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+categoryId);
        return result;
    }
}
