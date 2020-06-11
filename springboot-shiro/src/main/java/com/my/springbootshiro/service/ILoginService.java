package com.my.springbootshiro.service;

import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;

import java.util.Map;

public interface ILoginService {

    User addUser(Map<String, Object> map);

    Role addRole(Map<String, Object> map);

    User findByName(String name);

}
