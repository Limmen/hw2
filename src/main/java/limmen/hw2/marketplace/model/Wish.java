/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Wish remote-interface
 * @author kim
 */
public interface Wish extends Remote {
    
    public Item getItem() throws RemoteException;
    public String getUser() throws RemoteException;
}
