package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Autowired
    private ItemService itemService;
    //添加商品到购物车
    @Override
    public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list=getCartItemList(request);
        boolean haveFlg=false;
        for (CartItem cartItem:list) {
            if (cartItem.getId().longValue()==itemId){
                cartItem.setNum(cartItem.getNum()+num);
                haveFlg=true;
                break;
            }
        }
        if (!haveFlg){
            TbItem item=itemService.getItemById(itemId);
            CartItem cartItem=new CartItem();
            cartItem.setNum(num);
            cartItem.setId(itemId);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            String image = item.getImage();
            if (StringUtils.isNoneBlank(image)){
                String[]images=image.split(",");
                cartItem.setImage(images[0]);
            }else {
                cartItem.setImage(null);
            }
            list.add(cartItem);


        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(list),COOKIE_EXPIRE,true);
        return null;
    }

    //私有获取购物车列表
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try{

            String json= CookieUtils.getCookieValue(request,"TT_CART",true);
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return  list==null?new ArrayList<CartItem>():list;
        }catch(Exception e){
        e.printStackTrace();
        return new ArrayList<CartItem>();
        }


    }
    //获得购物车列表
    @Override
    public List<CartItem> getCarts(HttpServletRequest request) {
        List<CartItem> list = getCartItemList(request);
        return list;
    }
    //购物车商品数量变更
    @Override
    public TaotaoResult updateCartNum(Long itemId,Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list=getCartItemList(request);
        for (CartItem cartItem:list) {
            if (cartItem.getId().longValue()==itemId){
                cartItem.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(list),COOKIE_EXPIRE,true);
        return null;
    }
    //删除购物车商品
    @Override
    public TaotaoResult deleteCart(Long itemId,HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list=getCartItemList(request);

        for (CartItem cartItem:list) {
            if (cartItem.getId().longValue()==itemId){
               list.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(list),COOKIE_EXPIRE,true);

        return null;
    }


}
