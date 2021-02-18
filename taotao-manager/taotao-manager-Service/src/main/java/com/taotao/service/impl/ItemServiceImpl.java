package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;


    @Override
    public TbItem getItemById(Long itemId) {
        //TbItem item = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        //创建查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        //判断list中是否为空
        TbItem item = null;
        if (list != null && list.size() > 0) {
            item = list.get(0);
        }
        return item;
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        TbItemExample example=new TbItemExample();
        PageHelper.startPage(page,rows);
        List<TbItem> list =itemMapper.selectByExample(example);
        EUDataGridResult result=new EUDataGridResult();
        result.setRows(list);
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) {
        //生成Id
        long itemId= IDUtils.genItemId();
        //补全属性
        item.setId(itemId);
        //商品状态 1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        //创建时间和更新时间
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        itemMapper.insert(item);
        //商品描述
        TbItemDesc itemDesc =new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述数据
        itemDescMapper.insert(itemDesc);
        //创建一个pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中插入数据
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateItem(TbItem item, String desc, String itemParam) {
        Long itemId=item.getId();
        TbItem newItem=itemMapper.selectByPrimaryKey(itemId);
        //更新时间
        Date date=new Date();
        newItem.setUpdated(date);
        //更新商品表
        itemMapper.updateByPrimaryKey(newItem);
        //商品描述
        TbItemDesc itemDesc =itemDescMapper.selectByPrimaryKey(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        //跟新商品描述数据
        itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
        //更新加商品规格参数
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list=itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0){
            TbItemParamItem itemParamItem=list.get(0);
            itemParamItem.setParamData(itemParam);
            itemParamItem.setUpdated(date);
            itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItem(Long itemId) {
    /*    TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(itemId);*/
        itemMapper.deleteByPrimaryKey(itemId);
        itemDescMapper.deleteByPrimaryKey(itemId);
        itemParamItemMapper.deleteByPrimaryKey(itemId);
        return TaotaoResult.ok(itemId);
    }


    @Override
    public TaotaoResult showItemDesc(Long itemId) {
        TbItemDesc item=itemDescMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(item);
    }

    @Override
    public TaotaoResult showItemQuery(Long itemId) {
        TbItem item=itemMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(item);
    }
    @Override
    public String getItemParamHtml(Long itemId) {
        //根据商品id查询规格参数
        TbItemParamItemExample example=new TbItemParamItemExample();
        itemParamItemMapper.selectByExample(example);
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list==null&&list.isEmpty()){
            return "";
        }
        //取规格参数
        TbItemParamItem itemParamItem=list.get(0);
        //取json数据
        String paramData=itemParamItem.getParamData();
        //转化为java对象
        List<Map> mapList= JsonUtils.jsonToList(paramData,Map.class);
        //遍历list
        //遍历list生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
            sb.append("		</tr>\n");
            //取规格项
            List<Map>mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
                sb.append("			<td>"+map2.get("v")+"</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();


    }

}

















