package com.taotao.sso.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {



    @RequestMapping("/page/login")
    public String showLogin(String redirectURL, Model model){
        model.addAttribute("redirect",redirectURL);
        return "login";
    }


    @RequestMapping("/user/register")
    public String showRegister(){
        return "register";
    }
}
