package org.example.rpc.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        SpringBeanManager.applicationContext = ctx;
    }
    // 根据类获取实例
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
