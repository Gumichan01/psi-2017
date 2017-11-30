package com.nhamil.protInt.server;

import com.nhamil.protInt.server.data.ListAdds;
import com.nhamil.protInt.server.data.ListClients;
import com.nhamil.protInt.server.network.RequestHandler;
import com.nhamil.protInt.server.network.RequestInterpreter;
import com.nhamil.protInt.server.utils.Config;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main (String [] args) throws IOException {
        OptionParser optionParser = new OptionParser( ){
            {
                nonOptions( "<port>" )
                        .describedAs( "Arguments : " ).ofType(String.class).isRequired();
            }
        };
        OptionSet set = null;
        try {
            // Parse arguments
            set = optionParser.parse(args);
        }catch (Exception e){
            optionParser.printHelpOn( System.out );
            System.exit(1);
        }

        if(set.nonOptionArguments().isEmpty() && set.nonOptionArguments().size() != 1){
            optionParser.printHelpOn( System.out );
            System.exit(1);
        }
        int port = Integer.parseInt((String) set.nonOptionArguments().get(0));
        RequestInterpreter interpreter = new RequestInterpreter(new ListAdds(), new ListClients());
        run_Server(port,interpreter);


    }

    public static void run_Server(int portNumber, RequestInterpreter interpreter) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started  at:  " + serverSocket.getInetAddress().getHostAddress());

        try{
            while (true){
                System.out.println("Waiting for a  connection...");
                Socket activeSocket = serverSocket.accept();

                System.out.println("Received a  connection from  " + activeSocket);

                new Thread(new RequestHandler(activeSocket,interpreter)).start(); // start a new thread
            }
        }catch (IOException e) {
            System.out.println(e.getStackTrace());
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
