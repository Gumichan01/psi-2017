package com.nhamil.protInt.client.interpreter;

import com.nhamil.protInt.client.interpreter.commands.*;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Exceptions.CommandNotFoundException;
import com.nhamil.protInt.client.utils.Config;

import java.util.HashMap;

public abstract class AbstractCmd {
    protected HashMap<String,Command> listCMD;
    protected Server server;

    protected AbstractCmd( Server server){
        this.server = server;
        listCMD = new HashMap<>();
        setCommandList();
    }

    private void setCommandList(){
        listCMD.put(CreateAdd.name(), new CreateAdd(server));
        listCMD.put(FetchList.name(), new FetchList(server));
        listCMD.put(GetAddContent.name(), new GetAddContent(server));
        listCMD.put(ChatCommand.name(), new ChatCommand(server));
        listCMD.put(RemoveAdd.name(), new RemoveAdd(server));
        listCMD.put(Send.name(), new Send(server));
    }

    protected Command getCommand(String name) throws CommandNotFoundException {
        if(!name.equals("")){
            if (listCMD.containsKey(name)) {
                return listCMD.get(name);
            } else if(name.equals("help")){
                help();
                throw new CommandNotFoundException("");
            } else if (name.equals("exit")){
                System.out.println("Bye Bye!!");
                System.exit(0);
                throw new CommandNotFoundException("");
            } else {
                throw new CommandNotFoundException("\nCommand {" + name + "} Not found.\nType help for more information.");
            }
        }else{
            throw new CommandNotFoundException("");
        }
    }

    protected void intro() {
        System.out.println("======================================");
        System.out.println("==== Client Interactive Interface ====");
        System.out.println("======= by Nicolas Hamilakis  ========");
        System.out.println("=== Type help for more information ===");
        System.out.println("======================================\n\n");
    }

    private void help(){
        System.out.println("--------------------------------------");
        System.out.println("--------- List of Commands -----------");
        System.out.println("--------------------------------------");
        String[] keys = listCMD.keySet().toArray(new String[listCMD.size()]);
        for(int i=0; i < listCMD.size(); i++){
            System.out.print( " - " + keys[i] + " : \n\t> ");
            System.out.println(listCMD.get(keys[i]).description()+ "\n");
        }
        System.out.print( " - " + "exit" + " : \n\t> ");
        System.out.println("exits the program!");

        System.out.println("--------------------------------------");
    }

    public abstract void run();

}
