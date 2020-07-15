package com.my.springbootshiro.repository;

import com.my.springbootshiro.dao.IUserDao;
import com.my.springbootshiro.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private IUserDao userDao;

    @Test
    void findByName() {
        User user = userDao.findByName("xsh");
        Assert.assertNotNull(user);
    }
}