package com.my.springbootshiro.service;

import com.my.springbootshiro.domain.User;

import java.util.List;

public interface IUserService {

    /**
     * 通过姓名查询人员权限
     * @param name
     * @return
     */
    User findPermissionByName(String name);

    List<User> findAll();

    User findByName(String name);
}
