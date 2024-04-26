package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvokerProxy implements InvocationHandler {
    String arg;
    public InvokerProxy(String arg){
        this.arg = arg;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(arg);
        return null;
    }
}
