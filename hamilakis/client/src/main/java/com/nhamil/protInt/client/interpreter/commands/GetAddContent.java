package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.ProtocolFormatter;
import com.nhamil.protInt.client.tcp.ResponseParsor;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;

import java.util.List;

public class GetAddContent extends AbstractCommand<String> {
    private final static String name = "get";


    public GetAddContent(Server server) {
        super(server);
    }

    @Override
    public String description() {
        return "Get the content of an add by its ID";
    }

    @Override
    public String help() {
        return "#> get [ID]";
    }

    @Override
    public String run(List<String> args) throws ServerCommunicationException, BadResponseException {
        int id = Integer.parseInt(args.get(1));
        String response = network().sendMessage(ProtocolFormatter.getAd(id));
        return ResponseParsor.addGetter(response, id);
    }


    public static String name(){
        return name;
    }

    public static String format(String title, String body, int id) {
        return String.format("Add : [%d] \nTitle : %s\nContent : %s\n/=======/",id,title,body);
    }
}
