package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogoutController  {
    @Autowired
    private LogoutService logoutService;
    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token){
        TaotaoResult result = logoutService.logout(token);
        return result;
    }

}
