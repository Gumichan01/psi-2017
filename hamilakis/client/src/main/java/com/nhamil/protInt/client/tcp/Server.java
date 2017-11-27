package com.nhamil.protInt.client.tcp;

import com.nhamil.protInt.client.utils.Exceptions.*;
import com.nhamil.protInt.client.utils.Log;

import java.io.*;
import java.net.Socket;


public class Server {
    private String ip;
    private int port;
    private Socket socket;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;



    public Server(String ip, int port) throws InvalidServerIPException, InvalidPortException {
        NetworkTools.isInvalidIP(ip);
        this.ip = ip;

        NetworkTools.isInvalidPort(port);
        this.port = port;

        try {
            this.socket = new Socket(ip,port);
            System.out.println("Started client  socket at " + socket.getLocalSocketAddress());
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter = new PrintWriter(socket.getOutputStream(), true);

            //Connect
            socketWriter.println(ProtocolFormatter.connection());
            String response = socketReader.readLine();
            ResponseParsor.connectionResponce(response);

        } catch (IOException | BadConnectionException e) {
            System.err.println("Connection Failed");
            System.exit(0);
        }

    }

    public String sendMessage(String msg) throws ServerCommunicationException {
        try {
            socketWriter.println(msg);
            socketWriter.flush();

            String response = socketReader.readLine();
            return response;
        } catch (IOException e) {
            throw new ServerCommunicationException();
        }
    }


    public void disconnect() throws ServerCommunicationException {
        socketWriter.println(ProtocolFormatter.disconnect());
    }

    public String getIpAdresss() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}
