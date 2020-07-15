package com.my.springbootshiro.dao;

import com.my.springbootshiro.domain.User;

import java.util.List;

public interface IUserDao {

    /**
     * 通过姓名查询人员权限
     * @param name
     * @return
     */
    User findPermissionByName(String name);

    List<User> findAll();

    User findByName(String name);

    Integer save(User user);
}