package org.example.rpc.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc.client")
public class RpcServerProperties {

    private String serverAddress = "127.0.0.1";

    private int serverPort = 10000;
}
