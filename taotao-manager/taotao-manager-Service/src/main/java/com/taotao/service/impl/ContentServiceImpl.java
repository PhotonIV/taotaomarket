package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public EUDataGridResult getContentCatList(Long categoryId, int page, int rows) {
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(page,rows);
        List<TbContent> list= contentMapper.selectByExample(example);
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        PageInfo<TbContent> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long id) {
        TbContent content=contentMapper.selectByPrimaryKey(id);
        Long categoryId = content.getCategoryId();
        contentMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok(categoryId);
    }
    @Override
    public TaotaoResult updateContent(Long id, Long categoryId, TbContent content) {
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbContent> list= contentMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0) {
            TbContent tbContent=list.get(0);
            tbContent.setUpdated(new Date());
            //将json数据转为java对象
            String pic = JsonUtils.objectToJson(content.getPic());
            String pic2 = JsonUtils.objectToJson(content.getPic2());
            String subTitle = JsonUtils.objectToJson(content.getSubTitle());
            String title = JsonUtils.objectToJson(content.getTitle());
            String titleDesc = JsonUtils.objectToJson(content.getTitleDesc());
            String url = JsonUtils.objectToJson(content.getUrl());
            String content1 = JsonUtils.objectToJson(content.getContent());

            //去双引号
            pic= pic.replace("\"", "");
            pic2= pic2.replace("\"", "");
            subTitle= subTitle.replace("\"", "");
            title= title.replace("\"", "");
            titleDesc= titleDesc.replace("\"", "");
            content1= content1.replace("\"", "");
            url= url.replace("\"", "");


            tbContent.setPic(pic);
            tbContent.setPic2(pic2);
            tbContent.setSubTitle(subTitle);
            tbContent.setTitle(title);
            tbContent.setTitleDesc(titleDesc);
            tbContent.setUrl(url);
            tbContent.setContent(content1);
            contentMapper.updateByPrimaryKey(tbContent);
        }
        return TaotaoResult.ok(categoryId);
    }
}
