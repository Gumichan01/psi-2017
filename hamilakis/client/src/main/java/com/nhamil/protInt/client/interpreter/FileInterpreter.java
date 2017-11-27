package com.nhamil.protInt.client.interpreter;

import com.nhamil.protInt.client.tcp.Server;

import java.io.File;

public class FileInterpreter extends AbstractCmd {
    File file;

    public FileInterpreter(File file, Server server){
        super(server);
        this.file = file;
    }

    public void run() {
        System.out.println("Proccessing file " + file.getName());
        System.out.println("Error : cannot proccess file : feature not implemented");
    }
}
