package com.nhamil.protInt.client.tcp;

import com.nhamil.protInt.client.interpreter.commands.FetchList;
import com.nhamil.protInt.client.interpreter.commands.GetAddContent;
import com.nhamil.protInt.client.utils.Exceptions.BadConnectionException;
import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import javafx.util.Pair;

import java.util.Arrays;

public class ResponseParsor {
    private static final String code = "code";
    private static final String OK = "OK";
    private static final String FAIL = "FAIL";
    private static final String listannonce = "listannonce";
    private static final String client = "client";
    private static final String annonce = "annonce";


    public static void connectionResponce(String msg) throws BadConnectionException {
        String arg[] = msg.split(":",0);
        //if(arg.length == 2 &&  arg[0].equals(code) && arg[1].equals(OK))
            return;
       /*
        else
            throw new BadConnectionException();
        */
    }


    public static String parseListAnnonce(String response) throws BadResponseException {
        String arg[] = response.split(":", 0);
        System.out.println(Arrays.toString(arg));
        return "ok";
        /*
        if (arg.length > 1) {

            if (arg[0].equals(listannonce)) {
                return FetchList.buildListString(arg);
            }else if(arg[0].equals(code)){
                return "List fetch failed";
            }
        }
        throw new BadResponseException("List Received was not Formatted according to Protocol");
        */
    }

    public static String parseAddCreateResponse(String response) throws BadResponseException {
        String arg[] = response.split(":", 0);
        if(arg.length == 3){
            if(arg[2].equals(OK))
                return "Ad Created";
            else if(arg[2].equals(FAIL))
                return "Ad Creation Failed";
        }
        throw new BadResponseException("Bad Response for Add Creation");
    }

    public static String deleteResponse(String response) throws BadResponseException {
        String arg[] = response.split(":", 0);
        if(arg.length == 3){
            if(arg[2].equals(OK))
                return "Ad Deleted";
            else if(arg[2].equals(FAIL))
                return "Ad Deletion Failed";
        }
        throw new BadResponseException("Bad Response for Add Deletion");
    }

    public static String addGetter(String response, int id) throws BadResponseException {
        String arg[] = response.split(":",0);

        if (arg.length == 3) {
            if (arg[0].equals(annonce)) {
                return GetAddContent.format(arg[1], arg[2], id);
            }
        }else if(arg.length > 1 && arg[0].equals(code)){
            return "Add fetch failed";
        }

        throw new BadResponseException("Ad received was not Formatted according to Protocol");
    }

    public static Pair<String,String> communicationRequest(String response) throws BadResponseException {
        String arg[] = response.split(":",0);

        if (arg.length == 3) {
            if (arg[0].equals(client)) {

                return new Pair<>(arg[1],arg[2]);
            }
        }else if(arg.length > 1 && arg[0].equals(code)){
           throw new BadResponseException("Correspondant Not Found");
        }
        throw new BadResponseException("Bad Response Format");
    }
}
