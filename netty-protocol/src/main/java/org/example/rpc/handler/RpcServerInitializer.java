package org.example.rpc.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.example.rpc.codec.RpcDecoder;
import org.example.rpc.codec.RpcEncoder;

public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 初始偏移量 + 长度位的长度
        ch.pipeline()
                .addLast(
                        new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                        12,
                        4,
                        0,
                        0
                        ))
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new RpcServerHandler());
    }
}
