package com.nhamil.protInt.client.utils;

import java.io.PrintStream;

public class Log {
    private static PrintStream out =  System.out;

    public static void print(String msg){
        out.println("[" + msg + "]");
    }
}
