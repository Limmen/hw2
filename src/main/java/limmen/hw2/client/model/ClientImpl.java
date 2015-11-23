/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import limmen.hw2.bank.model.Account;
import limmen.hw2.client.view.GuiController;

/**
 * Client implementation class. extends UniCastRemoteObject to automaticly 
 * export the remote object. This is neccessary to be able to accept incomming
 * callbacks that are invoked from remote by the server.
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    
    private final String name;
    private Account account;
    private final GuiController contr;
    
    public ClientImpl(String name, GuiController contr) throws RemoteException{
        this.name = name;
        this.contr = contr;
    }
    
    @Override
    public void wishNotification(String name, float price) throws RemoteException{
        contr.updateLog("WISH NOTIFICATION \n" + name +
                " have been listed on the marketplace for " + price);        
    }
    
    @Override
    public void itemNotification(String name, float price, Client client) throws RemoteException{
        contr.updateLog("ITEM NOTIFICATION \n" + client.getName() + " bought "
                + name + " from you for " + price);
        contr.updateSold();
        contr.updateForSale();
    }
    
    @Override
    public String getName()throws RemoteException {
        return name;
    }
    @Override
    public Account getAccount() throws RemoteException{
        return account;
    }
    
    @Override
    public void setAccount(Account account) throws RemoteException{
        this.account = account;
    }
    
}
