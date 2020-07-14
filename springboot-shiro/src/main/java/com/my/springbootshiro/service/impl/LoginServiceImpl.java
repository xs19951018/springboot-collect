package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.domain.Permission;
import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.repository.RoleRepository;
import com.my.springbootshiro.repository.UserRepository;
import com.my.springbootshiro.service.ILoginService;
import com.my.springbootshiro.utils.ShiroEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "common", key = "'user_all'")
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "common", key = "'permission_'+#name")
    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Integer register(User user) {
        // 1.判断当前用户名是否存在
        User checkUser = findByName(user.getName());
        if (checkUser != null) {
            return 0;
        }
        // 2.新增用户，密码进行加密
        String encryptionPwd = ShiroEncryption.shiroMd5Encryption(user.getPassword());
        user.setPassword(encryptionPwd);
        User save = userRepository.save(user);
        return 1;
    }
}
