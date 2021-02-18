package com.taotao.rest.controller;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/base")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TaotaoResult getItemById(@PathVariable Long itemId){
        try{
            TbItem item = itemService.getById(itemId);
            return TaotaoResult.ok(item);
        }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }


    }
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getDescById(@PathVariable Long itemId){
        try{
            TbItemDesc itemDesc = itemService.getDescById(itemId);
            return TaotaoResult.ok(itemDesc);
        }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getParamById(@PathVariable Long itemId){
        try{
            TbItemParamItem itemParam = itemService.getItemParamById(itemId);
            return TaotaoResult.ok(itemParam);

        }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }


    }







}
