package org.example.proxy;

import org.example.dao.IProxy;

public class Test {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy();
        IProxy good = clientProxy.clientProxy(IProxy.class, "good");
        System.out.println(good);
    }
}
