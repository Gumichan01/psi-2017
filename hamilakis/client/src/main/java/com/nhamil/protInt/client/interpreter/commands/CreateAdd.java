package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.ProtocolFormatter;
import com.nhamil.protInt.client.tcp.ResponseParsor;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;
import com.nhamil.protInt.client.utils.Log;

import java.util.List;
import java.util.Scanner;

public class CreateAdd extends AbstractCommand<String> {
    private final static String name = "create";


    public CreateAdd(Server server) {
        super(server);
    }

    @Override
    public String description() {
        return "Creates a new Add on the server";
    }

    @Override
    public String help() { return "#> create"; }

    @Override
    public String run(List<String> args) throws ServerCommunicationException, BadResponseException {
        Scanner scan=new Scanner(System.in);
        System.out.print("Title : ");
        String title = scan.nextLine();
        System.out.print("Body : ");
        String body = scan.nextLine();
        String response =  network().sendMessage(ProtocolFormatter.newAdd(title,body));
        return ResponseParsor.parseAddCreateResponse(response);
    }

    public static String name(){
        return name;
    }
}
