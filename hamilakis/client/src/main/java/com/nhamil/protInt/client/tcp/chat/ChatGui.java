package com.nhamil.protInt.client.tcp.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatGui extends JFrame {
    private JButton disconnectButton;
    private JButton sendButton;
    private Connector con;
    private JTextArea message;
    private JTextArea content;


    public ChatGui(Connector con) {
        setTitle("Chat");
        setSize(400, 300);
        setResizable(false);
        this.con = con;
        this.content = new JTextArea(10, 30);
        this.content.setEditable(false);
        this.message = new JTextArea(1, 30);
        this.message.setEditable(true);
        this.sendButton = new JButton("Send");
        this.sendButton.addActionListener(new SendMessage(message,content,con));
        this.disconnectButton = new JButton("Disconnect");


        //Scrollable messages
        JScrollPane areaScrollPane = new JScrollPane(content);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(content.getSize());


        // Add Stuff
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel general = new JPanel(new BorderLayout());

        top.add(disconnectButton, BorderLayout.WEST);
        top.add(new JLabel("Chatting with : " ), BorderLayout.EAST);
        bottom.add(message, BorderLayout.WEST);
        bottom.add(sendButton, BorderLayout.EAST);


        general.add(top, BorderLayout.NORTH);
        general.add(areaScrollPane, BorderLayout.CENTER);
        general.add(bottom, BorderLayout.SOUTH);

        this.add(general);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                on_close();
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                on_close();
            }
        });
    }

    public void appendContent(String cont, String user){
        synchronized (content){
            content.append(user + ">" + cont + "\n");
            content.update(content.getGraphics());
        }
    }

    public void close() {
        JOptionPane.showMessageDialog(this, "Disconnecting the chat !","Disconnection",
                JOptionPane.WARNING_MESSAGE);
        this.dispose();
    }

    public void on_close(){
        con.disconnect();
    }
}
