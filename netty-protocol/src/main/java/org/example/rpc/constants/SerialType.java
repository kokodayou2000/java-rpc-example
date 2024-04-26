package org.example.rpc.constants;

public enum SerialType {

    // json 序列化
    JSON_SERIAL((byte)1),
    // java xu
    JAVA_SERIAL((byte)2);

    private byte code;

    SerialType(byte code){
        this.code = code;
    }
    public byte code(){
        return this.code;
    }

}
