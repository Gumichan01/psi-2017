package com.nhamil.protInt.server.network;

import com.nhamil.protInt.server.data.Add;

import java.util.List;

/** ProtocolFormatter keywords and string formatting*/
public class ProtocolFormatter {
    /** ProtocolFormatter Keywords */
    private static final String add = "annonce";
    private static final String OK = "OK";
    private static final String FAIL = "FAIL";
    private static final String code = "code";
    private static final String listadd = "listannonce";
    private static final String list = "list";
    private static final String connect = "connect";
    private static final String disconnect = "disconnect";
    private static final String get = "get";
    private static final String delete = "del";
    private static final String communication = "com";
    private static final String client = "client";

    /** Client connected succesfully */
    public static String connectionOK() {
        return String.format("%s:%s", code,OK);
    }
    /** Return format for an Add */
    public static String formatAdd(String title, String body) {
        return String.format("%s:%s:%s", add, title, body);
    }
    /** Return Format for an Add list */
    public static String formatListAdd(List<Add> list){
        String result =   String.format("%s:|",listadd);

        for(int i=0; i < list.size(); i++){
            if(list.get(i) != null)
                result = String.format("%s%d;%s|", result, i,list.get(i).getTitle() );
        }
        return result;
    }
    /** Return format for a getlist error */
    public static String formatListError(){
        return String.format("%s:%s:%s",code,list,FAIL);
    }

    /** Return format for creating an Add : success*/
    public static String addOK(){
        return String.format("%s:ann:%s",code,OK);
    }

    /** Return format for creating an Add : failure */
    public static String addFails(){
        return String.format("%s:ann:%s",code,FAIL);
    }

    /** Generic fail code */
    public static String fail(){
        return String.format("%s:%s",code,FAIL);
    }

    /** Client connection keyword */
    public static String connect_value() {
        return connect;
    }

    /** Client addlist request keyword*/
    public static String list_value() {
        return list;
    }

    /** Client get add keyword*/
    public static String get_value() {
        return get;
    }

    /** Client add manipulation keyword */
    public static String add_value() {
        return add;
    }

    /** Client disconnection keyword */
    public static String disconnect_value() { return disconnect; }

    /** Client add delete keyword */
    public static String delete_value() {
        return delete;
    }
    /** Add delete failed response */
    public static String delFail() {
        return String.format("%s:%s:%s",code,delete,FAIL);
    }

    /** Add delete success response */
    public static String delSuccess(){
        return String.format("%s:%s:%s",code,delete,OK);
    }
    /** Client request for other client info keyword*/
    public static String comunication_value() {
        return communication;
    }

    /** Format Client chat server information*/
    public static String clientInfo(String ip, Integer chatPort) {
        return String.format("%s:%s:%d",client,ip,chatPort);
    }
    /** Fail message for client info */
    public static String clientInfoFailed() {
        return String.format("%s:%s:%s",code,communication,FAIL);
    }
}
