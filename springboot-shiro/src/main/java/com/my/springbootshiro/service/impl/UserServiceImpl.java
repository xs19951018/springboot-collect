package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.dao.IUserDao;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Cacheable(value = "common", key = "'loginUser_'+#name")
    @Override
    public User findPermissionByName(String name) {
        return userDao.findPermissionByName(name);
    }

    @Cacheable(value = "common", key = "'user_all'")
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Cacheable(value = "common", key = "'user_'+#name")
    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }
}
