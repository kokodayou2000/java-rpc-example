package org.example.proxy;

import java.lang.reflect.Proxy;

public class ClientProxy {
    public <T> T clientProxy(final Class<T> interfaceCls,String arg1){
        return (T) Proxy.newProxyInstance(
                interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},
                new InvokerProxy(arg1)
        );
    }
}
