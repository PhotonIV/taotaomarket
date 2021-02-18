package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.ItemService;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SearchDao searchDao;
    @Override
    public TaotaoResult importItem() throws Exception{
        List<SearchItem> list=itemMapper.getItemList();
        for (SearchItem item: list) {
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_price",item.getPrice());
            document.addField("item_image",item.getImage());
            document.addField("item_category_name",item.getCategory_name());
            document.addField("item_desc",item.getItem_desc());
            solrServer.add(document);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }
    @Override
    public TaotaoResult importItemNew() throws Exception{
            List<SearchItem> list=itemMapper.getItemList();
            int index =list.size()-1;
            SearchItem item=list.get(index);
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_price",item.getPrice());
            document.addField("item_image",item.getImage());
            document.addField("item_category_name",item.getCategory_name());
            document.addField("item_desc",item.getItem_desc());
            solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItem(Long id) throws Exception {
        solrServer.deleteById(String.valueOf(id));
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateItem(Long id) throws Exception{
        deleteItem(id);
        SearchItem item = itemMapper.getById(id);
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id",item.getId());
        document.addField("item_title",item.getTitle());
        document.addField("item_sell_point",item.getSell_point());
        document.addField("item_price",item.getPrice());
        document.addField("item_image",item.getImage());
        document.addField("item_category_name",item.getCategory_name());
        document.addField("item_desc",item.getItem_desc());
        solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult getById(Long id) throws Exception {
        SearchItem item = itemMapper.getById(id);
        return TaotaoResult.ok(item);
    }

}
