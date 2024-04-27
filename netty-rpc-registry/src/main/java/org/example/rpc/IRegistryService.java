package org.example.rpc;

public interface IRegistryService {
    void register();

    void discovery(String serviceName);
}
