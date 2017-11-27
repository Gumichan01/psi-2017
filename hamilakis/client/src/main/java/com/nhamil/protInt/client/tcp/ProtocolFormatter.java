package com.nhamil.protInt.client.tcp;

import com.nhamil.protInt.client.tcp.chat.ChatServer;
import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;

public class ProtocolFormatter {
    private static final String connect = "connect";
    private static final String disconnect = "disconnect";
    private static final String annonces = "annonce";
    private static final String list = "list";
    private static final String delete = "del";
    private static final String get = "get";
    private static final String communicate = "com";


    public static String connection() throws InvalidPortException {
       int port = ChatServer.getPort();
       return String.format("%s:%d",connect,port);
    }


    public static String disconnect() {
        return String.format("%s",disconnect);
    }

    public static String listannonces() {
        return String.format("%s:%s",annonces,list);
    }

    public static String newAdd(String title, String body) {
        return String.format("%s:%s:%s",annonces,title,body);
    }

    public static String deleteAd(int id) {
        return String.format("%s:%s:%d",annonces,delete,id);
    }

    public static String getAd(int id) {
        return String.format("%s:%s:%d",annonces,get,id);
    }

    public static String chatRequest(int id) {
        return String.format("%s:%s:%d",annonces,communicate,id);
    }
}
