package com.my.springbootshiro.controller;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author admin
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/findPermission")
    public User findPermission() {
        return userService.findPermissionByName("xsh");
    }

    @RequestMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/findByName")
    public User findByName() {
        return userService.findByName("xsh");
    }

}