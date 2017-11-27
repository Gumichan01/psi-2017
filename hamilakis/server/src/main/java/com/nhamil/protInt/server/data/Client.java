package com.nhamil.protInt.server.data;

import com.nhamil.protInt.server.network.ProtocolFormatter;
import com.nhamil.protInt.server.utils.exceptions.AddNotFoundException;
import java.util.ArrayList;

/** Data structure class for a Client */
public class Client implements NetworkData {
    private String ip;
    private int chatPort;
    private ArrayList<Integer> listofAds;
    private volatile int ID;

    public Client(String ip) {
        this.ip = ip;
        listofAds = new ArrayList<>();
    }

    public String getIp() {
        return ip;
    }

    public Integer getChatPort() {
        return chatPort;
    }

    public void setChatPort (Integer port){
        chatPort = port;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    public void new_add(int index){
        listofAds.add(index);
    }

    /** Remove all the adds created by client*/
    public void clearAdds(ListAdds addlist) {
        for(Integer i : listofAds){
            try{
                addlist.remove(i);
            }catch (AddNotFoundException e) {  }
        }
    }

    @Override
    public String format() {
        return ProtocolFormatter.clientInfo(this.getIp(),this.getChatPort());
    }
}
