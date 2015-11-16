/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import limmen.hw2.client.model.Client;

/**
 *
 * @author kim
 */
public interface ListedItem extends Remote {
    public Item getItem() throws RemoteException;
    public Client getSeller() throws RemoteException;
}
