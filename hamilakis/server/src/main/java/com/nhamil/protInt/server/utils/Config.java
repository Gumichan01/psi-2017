package com.nhamil.protInt.server.utils;

public class Config{
    private static final int waitQueue = 100;
    private static final String host = "localhost";

    public static int getQueueSize(){
        return waitQueue;
    }

    public static String getHost(){
        return host;
    }

}
