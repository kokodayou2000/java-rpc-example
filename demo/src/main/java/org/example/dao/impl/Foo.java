package org.example.dao.impl;

import org.example.dao.IProxy;

public class Foo implements IProxy {
    @Override
    public void run(String arg1, String arg2) {
        System.out.println("Foo: "+arg1+" : "+arg2);
    }
}
