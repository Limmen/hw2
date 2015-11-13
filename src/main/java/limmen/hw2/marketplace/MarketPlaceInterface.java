/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public interface MarketPlaceInterface extends Remote {
    
    public Item Buy(Item item) throws RemoteException;
    public void Sell(Item item) throws RemoteException;
    public void register(String name) throws RemoteException;
    public void deregister(String name) throws RemoteException;
    public ArrayList<Item> listItems() throws RemoteException;
    public void wish(Item item) throws RemoteException;
    
}
