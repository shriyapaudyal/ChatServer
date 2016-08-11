/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shriya.ChatServer.listner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shneha
 */
public class ClientHandler {

    private List<Client> clients = new ArrayList<>();

    public void addClient(Client c) {
        clients.add(c);
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client getByUsername(String userName) {
        for (Client c : clients) {
            if (c.getUsername().equalsIgnoreCase(userName)) {
                return c;
            }
        }
        return null;
    }

    public Client getBySocket(Socket socket) {
        for (Client c : clients) {
            if (c.getSocket().equals(socket)) {
                return c;
            }
        }
        return null;
    }

    public void broadcastMessage(String message) throws IOException {
        for (Client c : clients) {
            PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
            ps.println(message);
        }
    }

    public void PrivateMessage(String username,String message) throws IOException {
        Client client=getByUsername(username);
        if(client!=null){
             PrintStream ps = new PrintStream(client.getSocket().getOutputStream());
            ps.println(message);
        }else{
            System.out.println(username+"no found");
        }
    }
}
