package com.my.springbootshiro.controller;

import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.ILoginService;
import com.my.springbootshiro.utils.ResultVOUtil;
import com.my.springbootshiro.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
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
    public ResultVO login(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          @RequestParam("rememberMe") boolean rememberMe){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                name, password, rememberMe);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);

        } catch (UnknownAccountException e) {
            return ResultVOUtil.error(1, "账户不存在");
        } catch (IncorrectCredentialsException e) {
            return ResultVOUtil.error(2, "密码错误");
        } catch (AuthenticationException e) {
            return ResultVOUtil.error(3, "未知错误");
        }
        return ResultVOUtil.success();
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

    @RequiresRoles("guest")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create(){
        return "Create success!";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/test")
    public String test(){
        return "test!";
    }

    @RequiresPermissions("test")
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public String test2(){
        return "test2!";
    }

    @RequestMapping(value = "/user/info")
    public String user(){
        return "userinfo!";
    }

    @RequestMapping(value = "/findByName")
    public String findByName(){
        //User user = loginService.findByName("xsh");
        loginService.findAll();
        return "user:xsh";
    }
}