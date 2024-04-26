package org.example.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.constants.ReqType;
import org.example.rpc.core.*;
import org.example.rpc.spring.SpringBeanManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive Rpc Server Result");
        // 获取请求id
        long requestId = msg.getHeader().getRequestId();
        RpcFuture future = RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getContent()); // 在这里就返回结果了
    }
}
