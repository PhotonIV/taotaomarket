package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Autowired
    TbContentMapper contentMapper;


    @Override
    public List<EUTreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example=new TbContentCategoryExample();

        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList=new ArrayList<>();
        for (TbContentCategory tbContentCategory:list) {
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        return resultList;
    }
    @Override
    public TaotaoResult insertCatgory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        //'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入数据
        contentCategoryMapper.insert(contentCategory);
        //取返回的主键
        Long ID = contentCategory.getId();
        //判断父节点的isparent属性
        //查询父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //返回主键
        return TaotaoResult.ok(ID);
    }
    @Override
    public TaotaoResult updateCatgory(Long id, String name) {
        //创建一个pojo对象
        TbContentCategory contentCategory =contentCategoryMapper.selectByPrimaryKey(id);

        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        //取返回的主键
        Long ID = contentCategory.getId();
        return TaotaoResult.ok(ID);
    }
    @Override
    public TaotaoResult deleteCatgory(Long id) {

        TbContentCategory contentCategory =contentCategoryMapper.selectByPrimaryKey(id);

        Long parentId =contentCategory.getParentId();
        //判断是否为父节点
        if (contentCategory.getIsParent()){
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
            for (TbContentCategory tbContentCategory:list) {
                deleteCatgory(tbContentCategory.getId());
                contentMapper.deleteByPrimaryKey(tbContentCategory.getId());
            }

        }
        //判断节点的父节点是否还有节点
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
        //先删除节点，再取剩下相应parentId的list

            contentCategoryMapper.deleteByPrimaryKey(id);
            contentMapper.deleteByPrimaryKey(id);
            List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
            TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
            if (list.size()==0){
                    parentNode.setIsParent(false);
                    //更新父节点
                    contentCategoryMapper.updateByPrimaryKey(parentNode);
                }
        return TaotaoResult.ok(parentId);
    }



}