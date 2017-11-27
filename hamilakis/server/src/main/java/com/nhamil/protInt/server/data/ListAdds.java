package com.nhamil.protInt.server.data;

import com.nhamil.protInt.server.network.ProtocolFormatter;
import com.nhamil.protInt.server.utils.exceptions.AddNotFoundException;

import java.util.ArrayList;

/** Data Structure class for an Addlist */
public class ListAdds implements NetworkData {
    private volatile ArrayList<Add> listOfAdds;

    public ListAdds(){
        listOfAdds = new ArrayList<>();
    }

    /**
     * Remove an Add
     * @throws AddNotFoundException
     */
    public void remove(int i) throws AddNotFoundException {
        synchronized (listOfAdds){
            try{
                listOfAdds.set(i,null);
            }catch (IndexOutOfBoundsException e){
                throw new AddNotFoundException();
            }
        }
    }

    /**
     * Add a new Add
     * @param a
     * @return ID of add
     */
    public int add(Add a){
        int size;
        synchronized (listOfAdds){
            size = listOfAdds.size();
            listOfAdds.add(a);
        }
        return size;
    }

    /**
     * Get an Add
     * @param i ID if add
     * @return Returns the add
     * @throws AddNotFoundException when add is not found
     */
    public  Add get(int i) throws AddNotFoundException {
        synchronized (listOfAdds){
            try {
                if(listOfAdds.get(i) != null){
                    return listOfAdds.get(i);
                }
                throw new AddNotFoundException();
            }catch (IndexOutOfBoundsException e){
                throw new AddNotFoundException();
            }

        }
    }

    @Override
    public String format() {
        return ProtocolFormatter.formatListAdd(listOfAdds);
    }
}
