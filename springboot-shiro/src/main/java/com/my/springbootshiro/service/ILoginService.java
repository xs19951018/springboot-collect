package com.my.springbootshiro.service;

import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;

import java.util.List;
import java.util.Map;

public interface ILoginService {

    List<User> findAll();

    User findByName(String name);

    /**
     * 注册
     * @param user
     * @return
     */
    Integer register(User user);
}
