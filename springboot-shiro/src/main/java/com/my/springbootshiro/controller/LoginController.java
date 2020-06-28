package com.my.springbootshiro.controller;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.ILoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author admin
 */
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    // 登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@Param("user") User user){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getName(),
                user.getPassword());
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return "login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@Param("user") User user){
        String msg = null;
        Integer count = loginService.register(user);
        msg = count == 1 ? "register ok!" : "register false!";
        return msg;
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }

    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create(){
        return "Create success!";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/test")
    public String test(){
        return "test!";
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public String test2(){
        return "test2!";
    }
}