package org.example.rpc.spring.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 自动装配 RpcProviderAuto
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    // 注入依赖
    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties){
        return new SpringRpcProviderBean(rpcServerProperties.getServerAddress(),rpcServerProperties.getServerPort());
    }
}
