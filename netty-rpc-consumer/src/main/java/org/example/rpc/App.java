package org.example.rpc;

import org.example.rpc.proxy.RpcClientProxy;

public class App {
    public static void main(String[] args) {

        RpcClientProxy rcp = new RpcClientProxy();
        // 调用方
        IUserService userService = rcp.clientProxy(IUserService.class, "localhost", 8080);
        System.out.println(userService.saveUser("good"));
    }
}
