package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceIml implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;


    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        //根据cid查询参数模板
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list =itemParamMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0){
            TbItemParam tbItemParam=list.get(0);
            return TaotaoResult.ok(tbItemParam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertParam(Long cid, String paramData) {
        TbItemParam itemParam=new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }
    @Override
    public EUDataGridResult getItemParamList(int page, int rows) {
        TbItemParamExample example=new TbItemParamExample();
        PageHelper.startPage(page,rows);
        List<TbItemParam> list =itemParamMapper.selectByExampleWithBLOBs(example);
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        PageInfo<TbItemParam> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }


}
