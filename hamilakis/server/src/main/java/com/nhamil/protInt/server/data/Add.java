package com.nhamil.protInt.server.data;

import com.nhamil.protInt.server.network.ProtocolFormatter;

/** Data Structure class for an Add */
public class Add implements NetworkData{
    private volatile String title;
    private volatile String body;
    private volatile Client client;

    /**
     *
     * @param client owner of the add
     * @param title Title
     * @param body Content
     */
    public Add(Client client, String title, String body) {
        this.title = title;
        this.body = body;
        this.client = client;
    }

    /** Get Info of add owner */
    public String communicate(){
        return client.format();
    }


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String format() {
        return ProtocolFormatter.formatAdd(title,body);
    }
}
