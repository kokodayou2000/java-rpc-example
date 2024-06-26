package org.example.rpc.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

@Data
public class RpcFuture<T> {

    private Promise<T> promise;

    public RpcFuture(Promise<T> promise){
        this.promise = promise;
    }
}
