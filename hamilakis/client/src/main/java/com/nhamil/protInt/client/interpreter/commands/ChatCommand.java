package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.tcp.ProtocolFormatter;
import com.nhamil.protInt.client.tcp.ResponseParsor;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.tcp.chat.ChatClient;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidServerIPException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class ChatCommand extends AbstractCommand<String> {
    private final static String name = "chat";


    public ChatCommand(Server server) {
        super(server);
    }

    @Override
    public String description() {
        return "Launches a chat with Ad creator";
    }

    @Override
    public String help() {
        return "#> chat [ID]";
    }

    @Override
    public String run(List<String> args) throws ServerCommunicationException, BadResponseException {
        int id = Integer.parseInt(args.get(1));
        String response = network().sendMessage(ProtocolFormatter.chatRequest(id));
        Pair<String, String> clientID = ResponseParsor.communicationRequest(response);
        try {
            new Thread(new ChatClient(clientID.getKey(), Integer.parseInt(clientID.getValue()))).start();
        } catch (NumberFormatException | InvalidPortException  e){
            System.err.println("Bad Chat Port");
        }   catch (InvalidServerIPException e) {
            System.err.println("Bad Chat Server IP");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("Chatting with : %s:%s",clientID.getKey(), clientID.getValue());
    }

    public static String name(){
        return name;
    }
}
