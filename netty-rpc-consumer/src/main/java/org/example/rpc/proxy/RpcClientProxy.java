package org.example.rpc.proxy;

import java.lang.reflect.Proxy;

public class RpcClientProxy {
    public <T> T clientProxy(final Class<T> interfaceCls,final String host,int port){

        // 动态代理
        return (T) Proxy.newProxyInstance(
                interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},
                new RpcInvokerProxy(host,port)
                );
    }
}
