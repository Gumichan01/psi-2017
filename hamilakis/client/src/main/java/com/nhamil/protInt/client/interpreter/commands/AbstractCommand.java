package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.Server;

public abstract class AbstractCommand<T> implements Command<T> {
    private Server server;

    public AbstractCommand( Server server){
        this.server = server;
    }

    public Server network(){ return server;}

    public static String name(){
        return "";
    }

}
