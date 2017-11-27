package com.nhamil.protInt.client.tcp.chat;

import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;
import com.nhamil.protInt.client.tcp.NetworkTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable{
    private static int chatPort = 0;
    private ServerSocket hostServer = null;
    private Socket socket = null;



    public ChatServer(int port) throws InvalidPortException, IOException {
        NetworkTools.isInvalidPort(port);
        chatPort = port;
        hostServer = new ServerSocket(chatPort);
    }

    public static int getPort() throws InvalidPortException {
        if (chatPort != 0)
            return chatPort;
        throw new InvalidPortException();
    }

    @Override
    public void run() {

        while (true){
            try {
                socket = hostServer.accept();
                System.out.print("Received a  connection from  " + socket + "\n#>");
                new Thread(new ChatServerHandler(socket)).start(); // start a new thread

            } catch (IOException e) {

            }
        }
    }
}
