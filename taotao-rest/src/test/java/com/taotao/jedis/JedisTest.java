package com.taotao.jedis;

import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContentCategory;
import com.taotao.rest.compoment.JedisClient;
import com.taotao.rest.pojo.ItemCatResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JedisTest {
    @Autowired
    private JedisClient jedisClient;
    //单机版测试

    public void testJedisSingle() throws  Exception{
        String json = jedisClient.hget("REDIS_CATAGORY_KEY", 0l + "");
        if (!StringUtils.isBlank(json))
        {
            List<TbContentCategory> list = JsonUtils.jsonToList(json, TbContentCategory.class);
            ItemCatResult result=new ItemCatResult(list);


        }




    }

    public  void testJedisPool() throws  Exception{
        //创建一个连接池对象
        //系统应该是单例的
        JedisPool jedisPool=new JedisPool("192.168.25.153",6379);
        //从连接池获取一个连接
        Jedis jedis=jedisPool.getResource();
        String result=jedis.get("test");
        System.out.println(result);
        //jedis必须关闭
        jedis.close();

        //系统关闭连接池
        jedisPool.close();


    }
    //连接jedis集群

    public void testJedisCluster () throws Exception{
        Set<HostAndPort> nodes =new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.153",7001));
        nodes.add(new HostAndPort("192.168.25.153",7002));
        nodes.add(new HostAndPort("192.168.25.153",7003));
        nodes.add(new HostAndPort("192.168.25.153",7004));
        nodes.add(new HostAndPort("192.168.25.153",7005));
        nodes.add(new HostAndPort("192.168.25.153",7006));
        //在nodes中指定每个节点的地址
        JedisCluster jedisCluster=new JedisCluster(nodes);

        //jedisCluster单例，系统关闭时再关闭
        jedisCluster.set("name","99");
        jedisCluster.set("string","100");

        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("string"));
        jedisCluster.close();
    }

    public void testJedisClientSpring() throws Exception{
        //创建spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从容器获取对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("agaag", "asfasf");
        String string =jedisClient.get("agaag");
        System.out.println(string);


    }









}
