package org.example.rpc;

import org.example.rpc.proxy.RpcClientProxy;

@Deprecated
public class App {
    public static void main(String[] args) {

        RpcClientProxy rcp = new RpcClientProxy();
        // 调用方
        IUserService userService = rcp.clientProxy(IUserService.class, "192.168.101.1", 10000);
        System.out.println(userService.saveUser("good"));
    }
}
