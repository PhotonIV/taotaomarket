package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParmController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long cid){
        TaotaoResult result = itemParamService.getItemParamByCid(cid);
        return result;
            }
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertTtemParam(@PathVariable Long cid, String paramData){
        TaotaoResult result=itemParamService.insertParam(cid,paramData);
        return result;
    }
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getTbItemList(Integer page, Integer rows){
        EUDataGridResult result=itemParamService.getItemParamList(page,rows);
        return result;
    }



}






