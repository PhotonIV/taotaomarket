package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.compoment.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO}")
    private String ITEM_BASE_INFO_KEY;
     @Value("${ITEM_DESC_INFO}")
    private String ITEM_DESC_INFO_KEY;
    @Value("${ITEM_PARAM_INFO}")
    private String ITEM_PARAM_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public TbItem getById(Long itemId) {
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId+ ":"+ ITEM_BASE_INFO_KEY);
            if (StringUtils.isNoneBlank(json)){
               return JsonUtils.jsonToPojo(json,TbItem.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId+ ":"+ ITEM_BASE_INFO_KEY ,
                    JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId+ ":"+ ITEM_BASE_INFO_KEY ,
                    ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }
    @Override
    public TbItemDesc getDescById(Long itemId) {
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_INFO_KEY);
            if (StringUtils.isNoneBlank(json)){
                return JsonUtils.jsonToPojo(json,TbItemDesc.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_INFO_KEY,
                    JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_INFO_KEY,
                    ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }

        return itemDesc;
    }
    @Override
    public TbItemParamItem getItemParamById(Long itemId) {
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_INFO_KEY);
            if (StringUtils.isNoneBlank(json)){
                return JsonUtils.jsonToPojo(json,TbItemParamItem.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list=itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0){
            TbItemParamItem itemParamItem = list.get(0);
            try {
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_INFO_KEY,
                        JsonUtils.objectToJson(itemParamItem));
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_INFO_KEY,
                        ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();

            }
            return itemParamItem;
        }
        return null;
    }


}
