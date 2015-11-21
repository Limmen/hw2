/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import limmen.hw2.client.model.Client;

/**
 * Wish-implementation. extends UniCastRemoteObject to automaticly 
 * export the remote object.
 * @author kim
 */
public class WishImpl extends UnicastRemoteObject implements Wish{
    private final Item item;
    private final String user;
    
    public WishImpl(Item item, String user) throws RemoteException{
        this.item = item;
        this.user = user;
    }
    @Override
    public Item getItem() {
        return item;
    }
    @Override
    public String getUser() {
        return user;
    }
    
    
}
