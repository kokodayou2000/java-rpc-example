package org.example.rpc.spring.reference;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

// 动态代理使用 FactoryBean来做

/**
 * 工厂bean
 * 只有在 发现属性带有 @RemoteReference 注解的时候，才需要使用该工厂bean 给属性 注入 对象
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
    private String serviceAddress;
    private int servicePort;
    private Class<?> interfaceClass;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    public void init(){
       this.object = Proxy.newProxyInstance(
               interfaceClass.getClassLoader(),
               new Class<?>[]{interfaceClass},
               new RpcInvokerProxy(serviceAddress,servicePort)
               );
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }
}
