package com.nhamil.protInt.client.tcp.chat;

import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidServerException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidServerIPException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;
import com.nhamil.protInt.client.tcp.NetworkTools;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable, Connector{
    private Socket socket;
    private String ip;
    private int port;
    private ChatGui gui;
    private BufferedReader socketReader = null;
    private PrintWriter socketWriter = null;


    public ChatClient(String ip, int port) throws InvalidPortException, InvalidServerIPException, IOException {
        NetworkTools.isInvalidIP(ip);
        NetworkTools.isInvalidPort(port);
        this.ip = ip;
        this.port = port;
        this.socket = new Socket(ip,port);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new PrintWriter(socket.getOutputStream());
        gui = new ChatGui(this);
    }

    public void run() {
        String msg = null;
        startGUI();
        socketWriter.println("connect:"+ socket.getLocalPort());
        try {
            msg = socketReader.readLine();
        } catch (IOException e) {  }
        if(!msg.contains("OK")){
            disconnect();
            return;
        }
        while(true){
            try{
                msg = socketReader.readLine();
                String stuff[] = msg.split(":",0);
                if(stuff.length == 2 && stuff[0].equals("msg")){
                    gui.appendContent(stuff[1], getCorrespondant());
                }else if(stuff.length == 1 && stuff[0].equals("disconnect")){
                    disconnect();
                    break;
                }
            }catch(IOException | NullPointerException e){
                disconnect();
                break;
            }
        }
    }

    @Override
    public void sendMessage(String msg) {
        socketWriter.println("msg:" + msg);
        socketWriter.flush();
    }

    @Override
    public String getCorrespondant() {
        return socket.getInetAddress().toString();
    }

    @Override
    public void disconnect() {
        socketWriter.println("disconnect");
        socketWriter.flush();
        gui.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startGUI(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
            }
        }).start();
    }
}
