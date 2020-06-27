package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.domain.Permission;
import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.repository.RoleRepository;
import com.my.springbootshiro.repository.UserRepository;
import com.my.springbootshiro.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ILoginServiceImpl implements ILoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
