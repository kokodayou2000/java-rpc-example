package org.example.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.constants.ReqType;
import org.example.rpc.constants.RpcConstant;
import org.example.rpc.core.Header;
import org.example.rpc.core.RpcProtocol;
import org.example.rpc.core.RpcRequest;
import org.example.rpc.core.RpcResponse;
import org.example.rpc.serial.ISerializer;
import org.example.rpc.serial.SerializerManager;

import java.util.List;

@Data
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("begin RpcEncoder");
        // 获取header
        Header header = msg.getHeader();
        // 写入
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());

        // 根据序列化类型获取序列化处理器
        ISerializer iSerializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] data = iSerializer.serializer(msg.getContent());
        header.setLength(data.length);
        // 写入数据的长度
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
