package com.my.springbootshiro.dao;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IUserDAOTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserPermissionById() {
        User user = userRepository.findByName("xsh");
        Assert.assertNotNull(user);
        Assert.assertEquals(2, user.getRoles().size());
        Assert.assertEquals(2, user.getRoles().get(0).getPermissions());
    }
}