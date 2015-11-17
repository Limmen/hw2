/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.hw2.client.model.Client;
import limmen.hw2.client.util.RejectedException;

/**
 *
 * @author kim
 */
public interface MarketPlace extends Remote {
    
    public void Buy(String name, String descr, float price, String seller, Client client) throws RemoteException;
    public void Sell(String name, String descr, float price, Client client) throws RemoteException;
    public void register(Client client) throws RemoteException, RejectedException;
    public void deRegister(Client client) throws RemoteException;
    public ArrayList<ListedItem> listItems() throws RemoteException;
    public ArrayList<Client> listClients() throws RemoteException;
    public ArrayList<String> getWishes(Client client) throws RemoteException;
    public ArrayList<ListedItem> getForSale(Client client) throws RemoteException;
    public void wish(String name,Client client) throws RemoteException;
    
}
