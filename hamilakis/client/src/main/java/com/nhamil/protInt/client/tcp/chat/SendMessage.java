package com.nhamil.protInt.client.tcp.chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMessage implements ActionListener {
    private JTextArea sender;
    private JTextArea content;
    private Connector con;


    public SendMessage(JTextArea sender, JTextArea content, Connector con) {
        this.sender = sender;
        this.content = content;
        this.con = con;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (content){
            String msg = sender.getText();
            sender.setText("");
            content.append("me>" + msg + "\n");
            con.sendMessage(msg);
            content.update(content.getGraphics());
        }
    }
}
