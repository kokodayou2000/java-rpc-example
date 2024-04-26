package org.example.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Data;
import org.example.rpc.constants.ReqType;
import org.example.rpc.constants.RpcConstant;


import java.util.List;
import org.example.rpc.core.Header;
import org.example.rpc.core.RpcProtocol;
import org.example.rpc.core.RpcRequest;
import org.example.rpc.core.RpcResponse;
import org.example.rpc.serial.ISerializer;
import org.example.rpc.serial.SerializerManager;

@Data
public class RpcDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < RpcConstant.HEADER_TOTAL_LEN){
            return;
        }
        // 标记读取开始索引
        in.markReaderIndex();
        // 读取两个字节的 magic number
        short magicNum = in.readShort();
        if (magicNum != RpcConstant.MAGIC){
            throw new IllegalArgumentException();
        }
        // 读取一个字节 的序列化类型
        byte serialType = in.readByte();
        // 读取一个字节 的请求类型
        byte reqType = in.readByte();
        // 读取八个字节 的请求id
        long requestId = in.readLong();
        // 读取四个字节 的报文长度
        int dataLength = in.readInt();

        // 获取剩余的数据，是否大于报文长度
        if (in.readableBytes() < dataLength){
            // 数据还原
            in.resetReaderIndex();
            return;
        }

        // 报文内容
        byte[] content = new byte[dataLength];

        // 将数据写入到content中
        in.readBytes(content);

        // 将数据拼装成 Header
        Header header = new Header(magicNum,serialType,reqType,requestId,dataLength);
        // 设置解析器
        ISerializer serializer = SerializerManager.getSerializer(serialType);
        // 获取请求类型
        ReqType rt = ReqType.findByCode(reqType);
        // 根据请求类型进行解码
        switch (rt){
            case REQUEST:
                // 将content反序列化
                RpcRequest request = serializer.deserializer(content, RpcRequest.class);
                RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();
                requestRpcProtocol.setHeader(header);
                requestRpcProtocol.setContent(request);
                out.add(requestRpcProtocol);
                break;
            case RESPONSE:
                // 将content反序列化
                RpcResponse response = serializer.deserializer(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resRpcProtocol = new RpcProtocol<>();
                resRpcProtocol.setHeader(header);
                resRpcProtocol.setContent(response);
                out.add(resRpcProtocol);
                break;
            case HEARTBEAT:
                // TODO
                break;
            default:
                break;
        }

    }
}
