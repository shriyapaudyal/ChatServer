/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shriya.ChatServer.listner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author shneha
 */
public class ClientListner extends Thread {

    private Socket socket;
    private Client client;
    private ClientHandler handler;

    public ClientListner(Socket socket, ClientHandler handler) {
        this.socket = socket;
        this.handler = handler;

    }

    @Override
    public void run() {
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("Welcome to chat room");
            ps.println("Enter your name :");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String name = reader.readLine();
            System.out.println("Hello" + name);

            client = new Client(socket, name);
            handler.addClient(client);
            handler.broadcastMessage(client.getUsername() + "has joined the chat room");
            while (!isInterrupted()) {
                String msg = reader.readLine();
                String[] token = msg.split(";;");
                if (token[0].equalsIgnoreCase("pm")) {
                    if(token.length>2){
                        handler.PrivateMessage(token[1],"PM from"+ client.getUsername() + ">>" +token[2]);
                    }

                } 
                else {
                    handler.broadcastMessage(client.getUsername() + ">>" + msg);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
