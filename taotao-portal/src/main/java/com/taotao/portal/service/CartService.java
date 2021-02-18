package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCarts( HttpServletRequest request);
    TaotaoResult updateCartNum(Long itemId,Integer num, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult deleteCart(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
