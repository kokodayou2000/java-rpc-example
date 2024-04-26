package org.example.rpc.serial;

public interface ISerializer {

    // 序列化
    <T> byte[] serializer(T obj);

    // 反序列化
    <T> T deserializer(byte[] data,Class<T> clazz);

    // 序列化的类型
    byte getType();
}
