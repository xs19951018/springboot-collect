package com.my.springbootshiro.dao;

import com.my.springbootshiro.domain.User;

public interface IUserDAO {

    /**
     * 通过id查询人员信息及权限
     * @param id
     * @return
     */
    User findUserPermissionById(String id);
}
