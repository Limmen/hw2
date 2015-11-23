/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import limmen.hw2.bank.model.Account;

/**
 * Remote interface for the client. This interface is used by the server
 * to initialize callbacks.
 * @author kim
 */
public interface Client extends Remote {
     
    public void wishNotification(String name, float price) throws RemoteException;
    public void itemNotification(String name, float price, Client client) throws RemoteException;
    public String getName() throws RemoteException;
    public Account getAccount() throws RemoteException;
    public void setAccount(Account acc) throws RemoteException;
    
}
