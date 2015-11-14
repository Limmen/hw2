/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author kim
 */
public interface Item extends Remote {
    
    public String getName() throws RemoteException;
    public String getDescription() throws RemoteException;
    public int getPrice() throws RemoteException;
}
