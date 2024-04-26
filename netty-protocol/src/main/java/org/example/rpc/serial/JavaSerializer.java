package org.example.rpc.serial;

import org.example.rpc.constants.SerialType;

import java.io.*;

public class JavaSerializer implements ISerializer{

    @Override
    public <T> byte[] serializer(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos);){
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserializer(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return (T)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public byte getType() {
        return SerialType.JAVA_SERIAL.code();
    }
}
