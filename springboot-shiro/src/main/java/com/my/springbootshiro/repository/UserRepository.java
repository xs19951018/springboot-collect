package com.my.springbootshiro.repository;

import com.my.springbootshiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 通过名称查询用户
     * @param name
     * @return
     */
    User findByName(String name);
}
