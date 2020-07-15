package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.dao.IUserDao;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedisServiceImp implements RedisService {

    @Autowired
    private IUserDao userDao;


    @Cacheable(value = "common", key = "'redis_user_all'")
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Cacheable(value = "common", key = "'permission_'+#name")
    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

}
