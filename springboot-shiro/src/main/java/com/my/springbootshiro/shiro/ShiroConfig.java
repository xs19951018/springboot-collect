package com.my.springbootshiro.shiro;

import com.my.springbootshiro.filter.MyShiroUrlFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 配置加密策略
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        // 自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("myFilter", new MyShiroUrlFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        Map<String,String> map = new LinkedHashMap<>();
        // 静态资源
        map.put("/public/**", "anon");
        // 匿名访问资源
        map.put("/logout", "anon");
        map.put("/login", "anon");
        // rememberMe访问资源
        map.put("/user/**", "user");
        // 授权资源
        map.put("/**", "myFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /***
     * shiro的在进行密码验证的时候，将会在此进行匹配
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1);
        // 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        //hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * cookie对象
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应登录页面的checkbox的name
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为s
        cookie.setMaxAge(10*60);
        return cookie;
    }

    /**
     * cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
}
