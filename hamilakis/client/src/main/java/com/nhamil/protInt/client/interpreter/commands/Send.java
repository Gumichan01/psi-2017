package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;

import java.util.List;
/** Debug utility */
public class Send extends AbstractCommand<String> {
    private static final String name = "send";


    public Send(Server server) {
        super(server);
    }

    @Override
    public String description() {
        return "Send custom messages to server";
    }

    @Override
    public String help() {
        return "#> send [string]";
    }

    @Override
    public String run(List<String> args) throws ServerCommunicationException {
        String msg = "";
        for(int i = 1; i < args.size(); i++){
            msg += args.get(i);
        }
        String response = network().sendMessage(msg);

        return response;
    }

    public static String name(){
        return name;
    }
}
