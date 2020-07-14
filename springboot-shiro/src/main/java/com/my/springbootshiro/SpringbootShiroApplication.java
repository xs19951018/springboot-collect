package com.my.springbootshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(value = "com.my.springbootshiro.dao")
@SpringBootApplication
public class SpringbootShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroApplication.class, args);
    }

}
