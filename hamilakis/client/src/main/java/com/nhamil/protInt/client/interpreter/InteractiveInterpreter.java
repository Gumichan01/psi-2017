package com.nhamil.protInt.client.interpreter;


import com.nhamil.protInt.client.interpreter.commands.Command;
import com.nhamil.protInt.client.tcp.Server;
import com.nhamil.protInt.client.utils.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InteractiveInterpreter extends AbstractCmd {


    public InteractiveInterpreter(Server server) {
        super(server);
    }

    public void run(){
        String text;
        List<String> list;
        Command c;
        Scanner scan=new Scanner(System.in);
        intro();
        while(Config.loop){
            try{
                System.out.print(Config.cmd);
                text = scan.nextLine();
                list = parseCommand(text);
                if(!list.isEmpty() ){
                   c =  getCommand(list.get(0));
                   System.out.println(c.run(list));
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

    private List<String> parseCommand(String text) {
        String [] res = text.split(" ");
        return Arrays.asList(res);
    }
}
