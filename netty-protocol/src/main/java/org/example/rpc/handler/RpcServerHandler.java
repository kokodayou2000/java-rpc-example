package org.example.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.rpc.constants.ReqType;
import org.example.rpc.core.Header;
import org.example.rpc.core.RpcProtocol;
import org.example.rpc.core.RpcRequest;
import org.example.rpc.core.RpcResponse;
import org.example.rpc.spring.SpringBeanManager;
import org.example.rpc.spring.service.Mediator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
        Header header = msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());

        Object result = Mediator.getInstance().processor(msg.getContent());

        // 将 RpcRequest 利用反射类执行，RpcRequest 包含了执行必要的字段 方法名，参数。。。
//        Object result = invoke(msg.getContent());
        resProtocol.setHeader(header);

        RpcResponse response = new RpcResponse();
        response.setData(result);
        response.setMsg("success");

        resProtocol.setContent(response);

        ctx.writeAndFlush(resProtocol);
    }


    @Deprecated
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
}
