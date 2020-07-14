package com.my.springbootshiro.controller;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis测试controller
 */
@Slf4j
@RequestMapping("/redis")
@Controller
public class RedisController {

    @Resource
    private RedisService redisService;

    @ResponseBody
    @RequestMapping("/getAll")
    public List<User> getAll(){
        return redisService.findAll();
    }

}
