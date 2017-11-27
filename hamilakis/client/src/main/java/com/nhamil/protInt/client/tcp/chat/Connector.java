package com.nhamil.protInt.client.tcp.chat;

public interface Connector {

    void sendMessage(String msg);
    String getCorrespondant();
    void disconnect();
}
