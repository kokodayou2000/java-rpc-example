package org.example.rpc.protocol;

import io.netty.bootstrap.Bootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.core.RpcProtocol;
import org.example.rpc.core.RpcRequest;
import org.example.rpc.handler.RpcClientInitializer;


@Slf4j

public class NettyClient {

   private final Bootstrap bootstrap;

   private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

   private String serviceAddress;
   private int servicePort;

    public NettyClient(String serviceAddress, int servicePort) {
        log.info("begin init Netty Client, address {} ,port {} ",serviceAddress,servicePort);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        // Import 一定要 sync() 才能等待连接完成之后，才发送数据
        final ChannelFuture cf = bootstrap.connect(this.serviceAddress,this.servicePort).sync();
        // 发送数据到channel
        cf.addListener((listener)->{
            if (listener.isSuccess()){
                log.info("connect rpc server {} success",this.serviceAddress);
            }else{
                log.info("connect rpc server {} error",this.serviceAddress);
                cf.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        // writeAnfFlush 的结果会保存到 Promise 中
        cf.channel().writeAndFlush(protocol);
    }
}

