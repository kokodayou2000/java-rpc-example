package org.example.rpc.service;

import org.example.rpc.ISayService;
import org.example.rpc.annotation.RemoteReference;
import org.example.rpc.annotation.RemoteService;

@RemoteService
public class SayServiceImpl implements ISayService {

    @Override
    public String say() {
        return "say";
    }
}
