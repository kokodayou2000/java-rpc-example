package org.example.rpc;

import org.example.rpc.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 扫描 protocol 包下的 spring 和 provider 包下的 service
@ComponentScan(basePackages = {"org.example.rpc.spring.service","org.example.rpc.service"})
@SpringBootApplication
public class NettyRpcProvider {
    public static void main(String[] args) {
        // 启动spring容器
        SpringApplication.run(NettyRpcProvider.class);
    }
}
