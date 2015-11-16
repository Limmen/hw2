/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import limmen.hw2.bank.Account;

/**
 *
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    
    private String name;
    private Account account;
    
    public ClientImpl(String name) throws RemoteException{
        this.name = name;
    }

    @Override
    public void wishNotification() throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemNotification() throws RemoteException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println("Account set");
        this.account = account;
    }
   
}
