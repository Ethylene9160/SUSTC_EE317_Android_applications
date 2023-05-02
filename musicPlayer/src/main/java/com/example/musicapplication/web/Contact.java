package com.example.musicapplication.web;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public interface Contact {
    Socket getSocket();

    WebInterface getWebInterface();

    static Contact creatProxy(Object contact){
        if(!(contact instanceof Contact)) return null;
        return (Contact) Proxy.newProxyInstance(Contact.class.getClassLoader(),
                new Class[]{Contact.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        if(method.getName().equals("getSocket")){
//                        }
                        return method.invoke(contact, args);
                    }
                });
    }
}
