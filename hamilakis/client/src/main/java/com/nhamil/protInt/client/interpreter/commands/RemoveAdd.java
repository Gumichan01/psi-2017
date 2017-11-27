package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.ProtocolFormatter;
import com.nhamil.protInt.client.tcp.ResponseParsor;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;

import java.util.List;

public class RemoveAdd extends AbstractCommand<String> {
    private final static String name = "delete";


    public RemoveAdd(Server server) {
        super(server);
    }

    @Override
    public String description() {
        return "Allows to delete an Ad by its ID";
    }

    @Override
    public String help() {
        return "#> delete [id]";
    }

    @Override
    public String run(List<String> args) throws ServerCommunicationException, BadResponseException {
       int id = Integer.parseInt(args.get(1));
       String response = network().sendMessage(ProtocolFormatter.deleteAd(id));
       return ResponseParsor.deleteResponse(response);
    }

    public static String name(){
        return name;
    }
}
