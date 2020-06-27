package com.my.springbootshiro.filter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (SpringBeanFactoryUtils.applicationContext == null) {
            SpringBeanFactoryUtils.applicationContext = applicationContext;
        }

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //根据名称（@Resource 注解）
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //根据类型（@Autowired）
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
