package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.dao.IUserDao;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.ILoginService;
import com.my.springbootshiro.utils.ShiroEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public Integer register(User user) {
        // 1.判断当前用户名是否存在
        User checkUser = userDao.findByName(user.getName());
        if (checkUser != null) {
            return 0;
        }
        // 2.新增用户，密码进行加密
        String encryptionPwd = ShiroEncryption.shiroMd5Encryption(user.getPassword());
        user.setPassword(encryptionPwd);
        Integer count = userDao.save(user);
        return 1;
    }

    @Override
    public User findPermissionByName(String name) {
        return userDao.findPermissionByName(name);
    }
}
