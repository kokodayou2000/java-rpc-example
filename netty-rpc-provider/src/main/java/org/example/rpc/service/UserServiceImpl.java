package org.example.rpc.service;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.IUserService;
import org.example.rpc.annotation.RemoteService;
import org.springframework.stereotype.Service;

@Slf4j
@RemoteService
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(String username) {
        log.info("save user {}",username);
        return "save user: "+ username;
    }
}
