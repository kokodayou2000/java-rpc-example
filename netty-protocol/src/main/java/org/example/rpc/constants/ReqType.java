package org.example.rpc.constants;

public enum ReqType {

    // 请求
    REQUEST((byte)1),
    // 响应
    RESPONSE((byte)2),
    // 心跳
    HEARTBEAT((byte)3);

    private byte code;

    ReqType(byte code){
        this.code = code;
    }
    public byte code(){
        return this.code;
    }

    public static ReqType findByCode(int code){
        for (ReqType reqType: ReqType.values()){
            if (reqType.code == code){
                return reqType;
            }
        }
        return null;
    }
}
