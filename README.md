使用netty 实现了 简单的 RPC框架

- netty-protocol
  - 编解码
  - 请求结构
  - 响应结构
  - header
  - client handler
  - server handler
  - netty client
  - netty server
  - 使用spring 管理上下文
  - 序列化
  - 反序列化
- netty-rpc-api
  - 接口
- netty-rpc-consumer
  - 代理
  - 代理处理器
  - 
- netty-rpc-provider
  - 扫描包





使用注解来优化代码

当前使用硬编码的形式来实现服务代理的

``` java
private Object invoke(RpcRequest request){
    try {
        Class<?> clazz = Class.forName(request.getClassName());
        // 根据类获取实例
        Object bean = SpringBeanManager.getBean(clazz);
        // 根据方法名称和参数类似得到具体的方法
        Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
        return method.invoke(bean,request.getParams());
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
    }
}
```















