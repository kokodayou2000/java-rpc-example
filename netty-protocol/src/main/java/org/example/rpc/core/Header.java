package org.example.rpc.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class Header implements Serializable {

    // 魔数 2byte
    private short magic;

    // 序列化类型 1byte
    private byte serialType;

    // 请求类型 1byte
    private byte reqType;

    // 请求id 8byte
    private long requestId;

    // 消息长度 4byte
    private int length;

}
