package com.my.springbootshiro.dao;

import com.my.springbootshiro.domain.Permission;
import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IUserDaoTest {

    @Resource
    private IUserDao userDao;

    @Test
    void findPermissionByName() {
        User user = userDao.findPermissionByName("xsh");
        Assert.assertNotNull(user);
        List<Role> roleList = user.getRoleList();
        Assert.assertNotNull(roleList);
        List<Permission> permissionList = roleList.get(0).getPermissionList();
        Assert.assertNotNull(permissionList);
        System.out.println(user.toString());
        System.out.println(roleList.size());
        System.out.println(permissionList.size());
    }
}