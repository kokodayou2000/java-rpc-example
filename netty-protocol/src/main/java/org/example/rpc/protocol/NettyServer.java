package org.example.rpc.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.handler.RpcServerInitializer;

@Slf4j
@AllArgsConstructor
public class NettyServer {
    private String serviceAddress;

    private int servicePort;

    public void startNettyServer(){
        log.info("begin start Netty server");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap  = new ServerBootstrap();

        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RpcServerInitializer());
        try {
            ChannelFuture future = bootstrap.bind(this.serviceAddress,this.servicePort).sync();
            log.info("Server started Success on Port,{}",this.servicePort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
