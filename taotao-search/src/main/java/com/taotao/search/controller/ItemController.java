package com.taotao.search.controller;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("importAll")
    @ResponseBody
    public TaotaoResult importAll(){
        try {
            TaotaoResult result= itemService.importItem();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("importNew")
    @ResponseBody
    public TaotaoResult importNew(){
        try {
            TaotaoResult result= itemService.importItemNew();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("delete/{id}")
    @ResponseBody
    public TaotaoResult delete(@PathVariable Long id){
        try {
            TaotaoResult result= itemService.deleteItem(id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("update/{id}")
    @ResponseBody
    public TaotaoResult update(@PathVariable Long id){
        try {
            TaotaoResult result= itemService.updateItem(id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
