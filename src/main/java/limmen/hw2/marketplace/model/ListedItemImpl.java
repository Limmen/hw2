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
 * ListedItem implementation
 * @author kim
 */
public class ListedItemImpl extends UnicastRemoteObject implements ListedItem  {
    
    private final Item item;
    private final String seller;
    
    public ListedItemImpl(Item item, String seller) throws RemoteException{
        this.item = item;
        this.seller = seller;
    }
    @Override
    public Item getItem() throws RemoteException{
        return item;
    }
    @Override
    public String getSeller() throws RemoteException{
        return seller;
    }
    
    
}
