package org.example.rpc.spring.service;


import org.example.rpc.core.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mediator {

    // 维护 bean method的关系，通过key
    // such as
    // IUserService.setUserName -> [<IUserService>,<setUserName>]
    public static Map<String,BeanMethod> beanMethodMap = new ConcurrentHashMap<>();
    // 单例
    private volatile  static Mediator instance = null;
    private Mediator(){}
    public static Mediator getInstance(){
        if (instance == null){
            synchronized (Mediator.class){
                if (instance == null){
                    instance = new Mediator();
                }
            }
        }
        return instance;
    }

    public Object processor(RpcRequest request){
        String key = request.getClassName() + "." +request.getMethodName();
        BeanMethod beanMethod = beanMethodMap.get(key);
        // 得到beanMethod实例
        if (null == beanMethod){
            return null;
        }
        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();
        try {
            return method.invoke(bean,request.getParams());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
