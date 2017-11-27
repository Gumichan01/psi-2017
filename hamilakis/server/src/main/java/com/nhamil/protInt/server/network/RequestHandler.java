package com.nhamil.protInt.server.network;

import com.nhamil.protInt.server.data.Client;
import com.nhamil.protInt.server.utils.exceptions.ClientNotFound;
import com.nhamil.protInt.server.utils.exceptions.RequestFormatException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/** Server request handler */
public class RequestHandler implements Runnable{
    private RequestInterpreter parsor;
    private Socket activeSocket;
    private Client currentclient;

    /**
     *
     * @param activeSocket Current active socket
     * @param parsor Current Message parsor
     */
    public RequestHandler(Socket activeSocket, RequestInterpreter parsor){
        this.activeSocket = activeSocket;
        this.parsor = parsor;
    }


    @Override
    public void run() {
        BufferedReader socketReader = null;
        PrintWriter socketWriter = null;
        try {
            socketReader = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
            socketWriter = new PrintWriter(activeSocket.getOutputStream(), true);
        }catch (IOException e){
            e.printStackTrace();
        }
        /* Create new Client */
        currentclient = new Client(activeSocket.getInetAddress().getHostAddress());
        parsor.addClient(currentclient);

        try{
            while(true) {
                //add catch for dc from client
                String request = socketReader.readLine();
                System.out.println("Received:>" + request);
                try {
                    String response = parsor.parse(request, currentclient.getID());
                    System.out.println("sending>" + response);
                    socketWriter.println(response);
                    socketWriter.flush();
                }catch (ClientNotFound e){
                    activeSocket.close();
                }  catch (RequestFormatException e) {
                    socketWriter.println(ProtocolFormatter.fail());
                    socketWriter.flush();
                }
            }
        }catch (SocketException e){
            System.out.println( "Client has disconnected");
            parsor.safeRemove(currentclient);
            try {
                activeSocket.close();
            } catch (IOException e1) { }
        }catch (IOException e){
        }
    }
}
