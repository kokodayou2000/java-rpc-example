package org.example.rpc.spring.reference;

import lombok.Data;

@Data
public class RpcClientProperties {
    private String serviceAddress = "127.0.0.1";
    private int servicePort = 10000;
}
