package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.ProtocolFormatter;
import com.nhamil.protInt.client.tcp.ResponseParsor;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetchList extends AbstractCommand<String> {
    private final static String name = "list";

    public FetchList(Server server) {
        super(server);
    }

    public String description() {
        return "Fetches the list of ads from the Server";
    }

    @Override
    public String help() {return "#> list"; }

    public String run(List<String> args) throws ServerCommunicationException, BadResponseException {
        String response  = network().sendMessage(ProtocolFormatter.listannonces());
        String formattedResponse = ResponseParsor.parseListAnnonce(response);
        return formattedResponse;
    }

    public static String name(){
        return name;
    }



    public static String buildListString(String arg[]) {
        String str = arg[1];
        String lst[] = str.split("\\|");
        String result = String.format("[ == List of Ads == ]\n");

        for(String s : lst){
            if(!s.isEmpty()){
                String add[] = s.split(";",0);
                if(add.length == 2)
                    result = String.format("%s[ == [%s] : %s == ]\n",result,add[0],add[1]);
            }
        }
        return String.format("%s[ =============== ]", result);
    }
}
