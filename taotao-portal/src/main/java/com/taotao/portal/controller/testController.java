package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class testController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public TaotaoResult test(HttpServletRequest request, HttpServletResponse response){
        TbUser user = userService.getUserByToken(request, response);
        return TaotaoResult.ok(user);


    }




}
