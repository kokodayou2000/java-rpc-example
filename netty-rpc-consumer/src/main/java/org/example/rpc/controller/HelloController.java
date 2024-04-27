package org.example.rpc.controller;


import org.example.rpc.IUserService;
import org.example.rpc.annotation.RemoteReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 当spring扫描到这个字段的数据，就动态注入该实例
    @RemoteReference
    IUserService userService;

    @GetMapping("/hello")
    public String hello(){
        return userService.saveUser("hello");
    }
}
