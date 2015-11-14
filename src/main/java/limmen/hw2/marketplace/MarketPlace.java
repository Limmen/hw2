/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.hw2.client.Client;

/**
 *
 * @author kim
 */
public interface MarketPlace extends Remote {
    
    public Item Buy(Item item, Client client) throws RemoteException;
    public void Sell(Item item, Client client) throws RemoteException;
    public void register(String name) throws RemoteException;
    public void deregister(String name) throws RemoteException;
    public ArrayList<ListedItem> listItems() throws RemoteException;
    public void wish(Item item, Client client) throws RemoteException;
    
}
