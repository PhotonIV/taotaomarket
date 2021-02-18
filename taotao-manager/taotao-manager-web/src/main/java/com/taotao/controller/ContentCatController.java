package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCatController {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;


    @Autowired
    private ContentCategoryService contentCategoryService;
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EUTreeNode> list=contentCategoryService.getContentCatList(parentId);
        return list;
    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createNode(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.insertCatgory(parentId, name);

        return result;
    }
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateNode(Long id, String name) {
        TaotaoResult result = contentCategoryService.updateCatgory(id, name);
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        Long parentId=tbContentCategory.getParentId();
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteNode(Long id) {
        TaotaoResult result = contentCategoryService.deleteCatgory(id);
        Long parentId= (Long) result.getData();
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+id);
        return result;
    }


}
