package com.my.elasticsearch.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: itemcenter
 * @package: com.duozheng.itemcenter.common.utils
 * @description: Bean工具类
 * @author: MuYu
 * @create: 2019-03-04 23:16
 * @copyright: Copyright (c) 2019, muyu@duozhengdian.com All Rights Reserved.
 **/
@Component
public class BeanUtils implements ApplicationContextAware {

    /** 容器上下文 **/
    private static ApplicationContext applicationContext;

    /**
     * 默认构造方法，注入上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 获取 bean
     */
    public static <T> T getBean(Class<T> clazz) {
        T obj;
        try {
            obj = applicationContext.getBean(clazz);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    /**
     * 获取 bean 的类型

     */
    public static <T> List<T> getBeansOfType(Class<T> clazz) {
        Map<String, T> map;
        try {
            map = applicationContext.getBeansOfType(clazz);
        } catch (Exception e) {
            map = null;
        }
        return map == null ? null : new ArrayList<>(map.values());
    }


    /**
     * 获取所有被注解的 bean
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> anno) {
        Map<String, Object> map;
        try {
            map = applicationContext.getBeansWithAnnotation(anno);
        } catch (Exception e) {
            map = null;
        }
        return map;
    }
}
