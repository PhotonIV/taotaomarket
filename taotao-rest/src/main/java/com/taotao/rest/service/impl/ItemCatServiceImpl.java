package com.taotao.rest.service.impl;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;

import com.taotao.rest.compoment.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Value("${REDIS_CATAGORY_KEY}")
    private String REDIS_CATAGORY_KEY;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private JedisClient jedisClient;


    @Override
    public ItemCatResult getItemCatList() {
        List catList = getItemCatList(0l);
        ItemCatResult result=new ItemCatResult(catList);
        return result;
    }


    public List getItemCatList(Long parentId) {

       /* try{
            String json = jedisClient.hget(REDIS_CATAGORY_KEY, parentId + "");
            if (!StringUtils.isBlank(json))
            {
                List<TbItemCat> list = JsonUtils.jsonToList(json, TbItemCat.class);
                List resultList = new ArrayList<>();
                int index=0;
                for (TbItemCat tbItemCat : list) {
                    if (index==14){
                        break;
                    }
                    if (tbItemCat.getIsParent()) {
                        CatNode node = new CatNode();
                        node.setUrl("/products/"+tbItemCat.getId()+".html");
                        if (tbItemCat.getParentId() == 0) {
                            node.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                            index++;
                        } else node.setName(tbItemCat.getName());
                        node.setItems(getItemCatList(tbItemCat.getId()));
                        resultList.add(node);
                    } else {
                        //如果是叶子节点
                        String item = "/product/" + tbItemCat.getId() + ".html>|" + tbItemCat.getName();
                        resultList.add(item);
                    }
                }
                return resultList;
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList<>();
        int index=0;
        for (TbItemCat tbItemCat : list) {
            if (index==14){
                break;
            }
            if (tbItemCat.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/"+tbItemCat.getId()+".html");
                if (tbItemCat.getParentId() == 0) {
                    node.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                    index++;
                } else node.setName(tbItemCat.getName());
                node.setItems(getItemCatList(tbItemCat.getId()));
                resultList.add(node);
            } else {
                //如果是叶子节点
                String item = "/product/" + tbItemCat.getId() + ".html>|" + tbItemCat.getName();
                resultList.add(item);
            }
        }

        try {
            jedisClient.set("","");
            //规范化key使用hash
            //定义一个保存内容的key，hash每个项为parentId
            //value是list需要把list转换为json数据
            parentId=0l;
            jedisClient.hset(REDIS_CATAGORY_KEY,parentId+"", JsonUtils.objectToJson(resultList));
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
    @Override
    public TaotaoResult syncContent(Long parentId) {
        jedisClient.hdel(REDIS_CATAGORY_KEY, parentId + "");
        return TaotaoResult.ok();
    }
}
