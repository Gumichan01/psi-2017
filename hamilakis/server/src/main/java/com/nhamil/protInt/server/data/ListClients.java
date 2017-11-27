package com.nhamil.protInt.server.data;

import com.nhamil.protInt.server.utils.exceptions.ClientNotFound;

import java.util.ArrayList;

public class ListClients {
    private ArrayList<Client> listOfClients;
    private volatile int active_size = 0;

    public ListClients() {
        this.listOfClients = new ArrayList<>();
    }

    public int getSize(){
        synchronized (listOfClients){
            return active_size;
        }
    }

    public int add(Client c){
        int size;
        synchronized (listOfClients){
            size = listOfClients.size();
            listOfClients.add(c);
            active_size++;
        }
        return size;
    }

    public Client get(int i) throws ClientNotFound {
        try {
            if(listOfClients.get(i) != null)
                return listOfClients.get(i);
            throw new ClientNotFound();
        }catch(IndexOutOfBoundsException e){
            throw new ClientNotFound();
        }
    }

    public void remove(int i) {
        synchronized (listOfClients){
            try{
                if(listOfClients.get(i) != null){
                    listOfClients.set(i, null);
                    active_size--;
                }
            }catch (IndexOutOfBoundsException e){ }
        }
    }
}
