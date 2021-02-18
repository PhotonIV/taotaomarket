package com.taotao.rest.controller;

import com.taotao.common.pojo.ExceptionUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

/*
    @RequestMapping("/list")
    @ResponseBody
    public ItemCatResult getItemCatList(){
        ItemCatResult result = itemCatService.getItemCatList();
        return  result;

    }
*/

   /*
   第一种方法
    @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){

        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)){
            String json= JsonUtils.objectToJson(result);
            return result;
        }
        String json=JsonUtils.objectToJson(result);
        return  callback+"("+json+");";

    }*/
   @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
   @ResponseBody
   public Object getItemCatList(String callback){

       ItemCatResult result = itemCatService.getItemCatList();
       if (StringUtils.isBlank(callback)){
           String json= JsonUtils.objectToJson(result);
           return result;
       }
       MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
       mappingJacksonValue.setJsonpFunction(callback);
       return mappingJacksonValue;

   }
    @RequestMapping("/sync/content/catagory/{parentId}")
    @ResponseBody
    public TaotaoResult syncContent(@PathVariable Long parentId) {
        try {
            TaotaoResult result = itemCatService.syncContent(parentId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
