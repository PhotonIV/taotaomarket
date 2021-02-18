package com.taotao.dao;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDao {
    @Autowired
    private ItemMapper itemMapper;
    private SqlSession sqlSession;


    public void test1(){

        SearchItem item=itemMapper.getById((long) 1474391927);

       System.out.println();


    }
}
