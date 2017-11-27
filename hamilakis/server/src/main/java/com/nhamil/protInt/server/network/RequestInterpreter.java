package com.nhamil.protInt.server.network;

import com.nhamil.protInt.server.data.Add;
import com.nhamil.protInt.server.data.Client;
import com.nhamil.protInt.server.data.ListAdds;
import com.nhamil.protInt.server.data.ListClients;
import com.nhamil.protInt.server.utils.exceptions.AddNotFoundException;
import com.nhamil.protInt.server.utils.exceptions.ClientNotFound;
import com.nhamil.protInt.server.utils.exceptions.RequestFormatException;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestInterpreter {
    private ListAdds addlist;
    private ListClients clientList;



    public RequestInterpreter(ListAdds addlist, ListClients clientList) {
        this.addlist = addlist;
        this.clientList = clientList;
    }

    public String parse(String request, int currentClient) throws ClientNotFound, RequestFormatException {
        //Test if Client is active
        clientList.get(currentClient);

        String req[] = request.split(":",0);
        ArrayList<String> arg = new ArrayList<String>(Arrays.asList(req));
        System.out.println(Arrays.toString(req));

        if(req.length > 0){
            if(arg.get(0).equals(ProtocolFormatter.connect_value()) && arg.size() == 2){
                try{
                    clientList.get(currentClient).setChatPort(Integer.parseInt(arg.get(1)));
                }catch (NumberFormatException e){
                    throw  new RequestFormatException();
                }
                return ProtocolFormatter.connectionOK();
            }else if(req[0].equals(ProtocolFormatter.add_value())){
                arg.remove(0);
                return  handle_annonces(arg, currentClient);
            }else if(req[0].equals(ProtocolFormatter.disconnect_value())){
                clientList.get(currentClient).clearAdds(addlist);
                clientList.remove(currentClient);
                return "";
            }
        }
        throw new RequestFormatException();
    }

    public void addClient(Client client) {
        client.setID(clientList.add(client));
    }


    private String handle_annonces(ArrayList<String> arg, int currentClient ) throws ClientNotFound, RequestFormatException {
        if(arg.size() >= 1){
             if(arg.get(0).equals(ProtocolFormatter.list_value())){
                return addlist.format();
            }else if(arg.get(0).equals(ProtocolFormatter.get_value()) && arg.size() == 2){
                try{
                    int index = Integer.parseInt(arg.get(1));
                    return addlist.get(index).format();
                }catch (NumberFormatException e){
                    throw new RequestFormatException();
                }catch (AddNotFoundException e){
                    System.out.println("Add Not Found Exception");
                    return ProtocolFormatter.fail();
                }
            }else if(arg.get(0).equals(ProtocolFormatter.comunication_value())){
                 try {
                     return  addlist.get(Integer.parseInt(arg.get(1))).communicate();
                 } catch (AddNotFoundException e) {
                     return ProtocolFormatter.clientInfoFailed();
                 }
             }else if(arg.get(0).equals(ProtocolFormatter.delete_value())){
                try{
                    addlist.remove(Integer.parseInt(arg.get(1)));
                    return ProtocolFormatter.delSuccess();
                }catch (NumberFormatException e){
                    throw new RequestFormatException();
                }catch (AddNotFoundException e){
                    return ProtocolFormatter.delFail();
                }
            }else{ // Create add
                if(arg.size() == 2) {
                    Add a = new Add(clientList.get(currentClient), arg.get(0),arg.get(1));
                    System.out.println("=> created add {" + a.getTitle() + ", " + a.getBody() + " for client : " + currentClient);
                    int index = addlist.add(a);
                    clientList.get(currentClient).new_add(index);
                    return ProtocolFormatter.addOK();
                }
            }
        }
        throw new RequestFormatException();
    }

    public void safeRemove(Client currentclient) {
        currentclient.clearAdds(addlist);
        clientList.remove(currentclient.getID());
    }
}
