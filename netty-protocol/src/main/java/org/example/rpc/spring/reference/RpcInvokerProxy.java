package org.example.rpc.spring.reference;

import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.constants.ReqType;
import org.example.rpc.constants.RpcConstant;
import org.example.rpc.constants.SerialType;
import org.example.rpc.core.*;
import org.example.rpc.protocol.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();
        long req_id = RequestHolder.REQUEST_ID.incrementAndGet();

        Header header = new Header(
                RpcConstant.MAGIC,
                SerialType.JSON_SERIAL.code(),
                ReqType.REQUEST.code(),
                req_id,
                0
        );
        // 设置 header 创建 Rpc请求对象
        requestRpcProtocol.setHeader(header);
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        requestRpcProtocol.setContent(request);

        // 使用netty client将数据发出
        // 短连接
        NettyClient nettyClient = new NettyClient(host,port);

        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(req_id,future);

        // 执行sendRequest之后，结果会保存在 future 中
        nettyClient.sendRequest(requestRpcProtocol);
        // future.getPromise().get() 会一直阻塞，
        // 直到执行到  RpcClientHandler 的 future.getPromise().setSuccess(msg.getContent());
        return future.getPromise().get().getData();
    }
}
