package org.example.dao.impl;

import org.example.dao.IProxy;

public class Bar implements IProxy {
    @Override
    public void run(String arg1, String arg2) {
        System.out.println("Bar: "+arg1+" : "+arg2);
    }
}
