package com.nhamil.protInt.client.tcp.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatServerHandler implements Runnable,Connector {
    private Socket socket;
    private ChatGui gui;
    private BufferedReader socketReader = null;
    private PrintWriter socketWriter = null;
    

    public ChatServerHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.gui = new ChatGui(this);
            socketReader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        startGUI();
        String msg;
        try {
            msg = socketReader.readLine();
            String res[] = msg.split(":",0);
            if(res.length > 1 && res[0].equals("connect"))
                socketWriter.println("code:OK");
            else{
                disconnect();
                return;
            }
        } catch (IOException e) {
            disconnect();
            return;
        }
            while (true){
                try{
                    msg = socketReader.readLine();
                    String stuff[] = msg.split(":",0);
                    if(stuff.length == 2 && stuff[0].equals("msg")){
                        gui.appendContent(stuff[1], getCorrespondant());
                    }else if(stuff.length == 1 && stuff[0].equals("disconnect")){
                        disconnect();
                        break;
                    }

                }catch (IOException e){
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
        try {
            gui.close();
            socket.close();
        } catch (IOException e) {

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
