package com.my.springbootshiro.service;

import com.my.springbootshiro.domain.User;

import java.util.List;

/**
 * 定时任务service
 */
public interface RedisService {

    List<User> findAll();

    User findByName(String uuid);

}
