package com.my.springbootshiro.filter;

import com.my.springbootshiro.constant.SysContant;
import com.my.springbootshiro.domain.Permission;
import com.my.springbootshiro.domain.Role;
import com.my.springbootshiro.domain.User;
import com.my.springbootshiro.service.ILoginService;
import com.my.springbootshiro.service.IUserService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于shiro的url过滤器，判断用户对于资源的权限
 * @author xsh
 */
public class MyShiroUrlFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(MyShiroUrlFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        // 是否登录授权
        boolean isAuthenticated = subject.isAuthenticated();
        if (isAuthenticated) {
            // 已经登录，判断权限url权限
            User user = (User) subject.getPrincipal();
            // 1.查询登录着所有的角色及权限
            IUserService loginService = SpringBeanFactoryUtils.getBean(IUserService.class);
            User loginUser = loginService.findPermissionByName(user.getName());
            if (SysContant.SYS_ADMIN.equals(loginUser.getName())) {
                // 超级管理员直接放权
                return true;
            }
            // 权限集合
            List<String> permissionList = new ArrayList<>();
            for (Role role : loginUser.getRoleList()) {
                for (Permission permission : role.getPermissionList()) {
                    permissionList.add(permission.getPermission());
                }
            }

            // 2.获取当前访问的url
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String key = getUrlKey(httpRequest.getRequestURI());

            // 3.判断该用户是否具有该key资源的访问权限
            if (permissionList.indexOf(key) > -1) {
                // 存在
                return true;
            } else {
                // 不存在,鉴权失败
                // 4.判断请求类型，若为ajax请求，返回json
                String xRequestedWith = httpRequest.getHeader("X-Requested-With");
                if (xRequestedWith != null && "XMLHTTPREQUEST".equals(xRequestedWith.toUpperCase())) {
                    // 是ajax请求,返回json字符串
                    httpResponse.setContentType("text/json; charset=UTF-8");
                    httpResponse.getWriter().write("{\"code\":-1,\"msg\":\"没有资源的访问权限\"}");
                }else {
                    // 普通请求
                    this.saveRequestAndRedirectToLogin(request, response);
                }
                return false;
            }

        } else {
            // 未登录，转到登录请求
            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }

    }

    /**
     * 根据前端url获取urlKey
     * @param url
     * @return
     */
    protected String getUrlKey(String url){
        int beginIndex = -1;
        int endIndex = -1;

        beginIndex = url.lastIndexOf("/") + 1;
        endIndex = url.indexOf("?") > -1 ? url.indexOf("?") : url.length();

        return url.substring(beginIndex, endIndex);
    }
}
