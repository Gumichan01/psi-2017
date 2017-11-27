package com.nhamil.protInt.client.tcp;

import com.nhamil.protInt.client.utils.Exceptions.InvalidPortException;
import com.nhamil.protInt.client.utils.Exceptions.InvalidServerIPException;

public class NetworkTools {

    public static void isInvalidPort(int port) throws InvalidPortException {
        //FIXME test if port is valid
        if(port <= 1024) throw new InvalidPortException();
    }

    public static void isInvalidIP(String ip) throws InvalidServerIPException {
        //FIXME test if IP is valid
        if(ip.isEmpty()) throw new InvalidServerIPException();
    }
}
