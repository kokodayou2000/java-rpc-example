package org.example.rpc.handler;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.codec.RpcDecoder;
import org.example.rpc.codec.RpcEncoder;

@Slf4j
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("begin initializer ");
        ch.pipeline()
                .addLast(
                        new LengthFieldBasedFrameDecoder(
                                Integer.MAX_VALUE,
                                12,
                                4,
                                0,
                                0
                        ))
                .addLast(new LoggingHandler())
                .addLast(new RpcEncoder())
                .addLast(new RpcDecoder())
                .addLast(new RpcClientHandler());
    }
}
