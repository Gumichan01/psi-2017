package com.nhamil.protInt.client;

import com.nhamil.protInt.client.interpreter.AbstractCmd;
import com.nhamil.protInt.client.interpreter.FileInterpreter;
import com.nhamil.protInt.client.interpreter.InteractiveInterpreter;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.tcp.chat.ChatServer;
import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidServerIPException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Client {
    private static Boolean INTERACTIVE = true;
    private  static File cmdFile;
    private static AbstractCmd parser;
    private static Server server;
    public static ChatServer chatServer;


    public static void main(String [] args) throws IOException {
        OptionParser optionParser = new OptionParser( ){
            {
                accepts( "c" ).withRequiredArg().ofType( String.class )
                        .describedAs( "commandFile" );
                nonOptions( "<ip-serveur> <port-serveur> <port-msg>" ).ofType( File.class )
                        .describedAs( "Arguments : " ).ofType(String.class).isRequired();
            }
        };
        // Parse arguments
        OptionSet set =  optionParser.parse(args);

        //If arguments are empty print help & exit
        if(!set.hasOptions() && set.nonOptionArguments().size() != 3){
            optionParser.printHelpOn( System.out );
            System.exit(1);
        }
        /** Initialise Chat Server */
        int chatServerPort = 0;
        try{
            chatServerPort = Integer.parseInt((String) set.nonOptionArguments().get(2));
        }catch (NumberFormatException e){
            System.err.println("Chat Port need to be a Number");
            System.exit(2);
        }

        try {
            chatServer = new ChatServer(chatServerPort);
        } catch (InvalidPortException e) {
            System.err.println("error : chat port={" + chatServerPort + "} is not a valid Port number.");
            System.exit(2);
        }
        new Thread(chatServer).start();


        //Get server details
        String serverIp = (String) set.nonOptionArguments().get(0);
        int serverPort = Integer.parseInt((String) set.nonOptionArguments().get(1));

        //Connect to server
        try {
            server = new Server(serverIp,serverPort);
        } catch (InvalidServerIPException e) {
            System.err.println("error : server ip={" + serverIp + "} is not a valid IP address.");
            System.exit(2);
        } catch (InvalidPortException e) {
            System.err.println("error : server port={" + serverPort + "} is not a valid Port number.");
            System.exit(2);
        }


        //If command file was given load
        if( set.has("c") ){
            INTERACTIVE = false;
            try {
                cmdFile = new File((String) set.valueOf("c"));
                if(!cmdFile.exists()) throw new FileNotFoundException();
                parser = new FileInterpreter(cmdFile,server);
            }catch (FileNotFoundException e){
                System.err.println("Error argument " + (String) set.valueOf("c") + " is not a file");
                System.exit(2);
            }
        }else{
            //Load interactive parser
            parser = new InteractiveInterpreter(server);
        }


        parser.run();
    }
}
