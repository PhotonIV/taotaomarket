package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.compoment.JedisClient;
import com.taotao.sso.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.jar.JarEntry;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;


    @Autowired
    private JedisClient jedisClient;
    @Override
    public TaotaoResult logout(String token) {


        jedisClient.expire(REDIS_SESSION_KEY+":"+token,1);
        return TaotaoResult.ok();
    }
}
