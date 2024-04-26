package org.example.rpc.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {
    // 类名
    private String className;
    // 目标方法名称
    private String methodName;
    // 请求参数
    private Object[] params;
    // 参数类型
    private Class<?>[] parameterTypes;

}
