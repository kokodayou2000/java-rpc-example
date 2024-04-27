package org.example.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.example.rpc.spring.reference","org.example.rpc.controller","org.example.rpc.annotation"})
@SpringBootApplication
public class NettyConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(NettyConsumerMain.class);
    }
}
