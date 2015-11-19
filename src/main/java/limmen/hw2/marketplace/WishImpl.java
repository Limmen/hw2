/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import limmen.hw2.client.model.Client;

/**
 * Wish-implementation. extends UniCastRemoteObject to automaticly 
 * export the remote object.
 * @author kim
 */
public class WishImpl extends UnicastRemoteObject implements Wish{
    private final Client client;
    private final String name;
    private final float price;
    
    public WishImpl(String name, float price,  Client client) throws RemoteException{
        this.client = client;
        this.name = name;
        this.price = price;
    }
    @Override
    public Client getClient() {
        return client;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public float getPrice() {
        return price;
    }
    
    
}
