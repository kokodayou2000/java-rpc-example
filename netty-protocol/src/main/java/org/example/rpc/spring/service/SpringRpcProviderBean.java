package org.example.rpc.spring.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.annotation.RemoteService;
import org.example.rpc.protocol.NettyServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * InitializingBean 在bean初始化后触发 afterPropertiesSet
 * BeanPostProcessor 是在bean实例化触发前和触发后分别执行的回调
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {
    private final String serverAddress;
    private final int serverPort;

    public SpringRpcProviderBean(String serverAddress,int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host{},on port{}",this.serverAddress,this.serverPort);
        new Thread(()->{
            new NettyServer(this.serverAddress,this.serverPort).startNettyServer();
        }).start();
    }

    // bean 实例化后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 任何bean被装载到 spring 容器的时候就会触发回调
        // 只要bean声明了这个注解，就需要将该服务发布在网络上
        if (bean.getClass().isAnnotationPresent(RemoteService.class)){
            // 得到所有的方法
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String key = bean.getClass().getInterfaces()[0].getName() +"."+method.getName();
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key,beanMethod);
            }

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
