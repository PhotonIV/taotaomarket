package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class testItemCatService {
    @Autowired
    private ItemCatService itemCatService;

    public TaotaoResult test(){
        List<TbContentCategory> catList = (List<TbContentCategory>) itemCatService.getItemCatList();
        ItemCatResult result=new ItemCatResult(catList);
        return  TaotaoResult.ok(result);
    }


}
