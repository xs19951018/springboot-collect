package com.my.springbootshiro.service.impl;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.repository.UserRepository;
import com.my.springbootshiro.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedisServiceImp implements RedisService {

    @Autowired
    private UserRepository userRepository;


    @Cacheable(value = "common", key = "'redis_user_all'")
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "common", key = "'permission_'+#name")
    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

}
