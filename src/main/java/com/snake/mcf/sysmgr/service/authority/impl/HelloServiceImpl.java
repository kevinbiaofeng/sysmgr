package com.snake.mcf.sysmgr.service.authority.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.sysmgr.mapper.authority.HelloMapper;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;
import com.snake.mcf.sysmgr.repertory.mapper.TbMerchantMapper;
import com.snake.mcf.sysmgr.service.authority.HelloService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HelloServiceImpl
 * @Author 大帅
 * @Date 2019/6/19 16:51
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private TbMerchantMapper merchantMapper;

    @Autowired
    private HelloMapper helloMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String test() {

        try {
            String a = stringRedisTemplate.boundValueOps("a").get();
            stringRedisTemplate.delete("a");
            String user = stringRedisTemplate.boundValueOps("user").get();
            if(StringUtils.isNotEmpty(user)){
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TbMerchant> list = merchantMapper.selectAll();

        String s = JsonUtils.toString(list);

        try {
            stringRedisTemplate.boundValueOps("user").set(s,300, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(redisTemplate);

        System.out.println(stringRedisTemplate);

        return  s;
    }
}
