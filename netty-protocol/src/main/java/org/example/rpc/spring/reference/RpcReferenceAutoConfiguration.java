package org.example.rpc.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RpcReferenceAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    // 注入到spring 容器中
    @Bean
    public SpringRpcReferencePostProcessor postProcessor(){
        RpcClientProperties rpcClientProperties = new RpcClientProperties();
        String address = this.environment.getProperty("rpc.client.serviceAddress");
        rpcClientProperties.setServiceAddress(address);
        String port = this.environment.getProperty("rpc.client.servicePort");
        int portNum = Integer.parseInt(port);
        rpcClientProperties.setServicePort(portNum);
//        rpcClientProperties.setServiceAddress("127.0.0.1");
//        rpcClientProperties.setServicePort(10000);
        return new SpringRpcReferencePostProcessor(rpcClientProperties);
    }
}
