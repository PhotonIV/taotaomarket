package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Value("${SEARCH_SOLR_BASE_URL}")
    private  String SEARCH_SOLR_BASE_URL;
    @Value("${SOLR_TYPE_DELETE}")
    private  String SOLR_TYPE_DELETE;
    @Value("${SOLR_TYPE_ADD}")
    private  String SOLR_TYPE_ADD;
    @Value("${SOLR_TYPE_UPDATE}")
    private  String SOLR_TYPE_UPDATE;

    @Autowired
    private ItemService itemService;
    //根据商品id查询商品
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId) {
        TbItem item = itemService.getItemById(itemId);
        return item;
    }
    //查询商品列表
    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult getTbItemList(Integer page, Integer rows){
        EUDataGridResult result=itemService.getItemList(page,rows);
        return result;
    }
    //新增商品
    @RequestMapping(value="/item/save", method=RequestMethod.POST)
    @ResponseBody
    private TaotaoResult createItem(TbItem item, String desc, String itemParams) {
        TaotaoResult result = itemService.createItem(item, desc, itemParams);
        HttpClientUtil.doGet(SEARCH_SOLR_BASE_URL+SOLR_TYPE_ADD);
        return result;
    }
    //展示商品规格界面
    @RequestMapping("/page/item/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String html = itemService.getItemParamHtml(itemId);
        model.addAttribute("myhtml", html);
        return "itemparam";
    }
    //删除商品
    @RequestMapping("/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(Long ids) {
        TaotaoResult result=itemService.deleteItem(ids);
        HttpClientUtil.doGet(SEARCH_SOLR_BASE_URL+SOLR_TYPE_DELETE+ids);
        return result;
    }

    //返回商品param数据
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    @ResponseBody
    private TaotaoResult showItemQuery(@PathVariable Long itemId) {
        TaotaoResult result = itemService.showItemQuery(itemId);
        return result;
    }
    //返回商品query数据
    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    private TaotaoResult showItemById(@PathVariable Long itemId) {
        TaotaoResult result = itemService.showItemDesc(itemId);
        return result;
    }
    //更新商品
    @RequestMapping(value="rest/item/update", method=RequestMethod.POST)
    @ResponseBody
    private TaotaoResult updateItem(TbItem item, String desc, String itemParams) {
        TaotaoResult result = itemService.updateItem(item, desc, itemParams);
        HttpClientUtil.doGet(SEARCH_SOLR_BASE_URL+SOLR_TYPE_UPDATE+item.getId());
        return result;
    }

}



















