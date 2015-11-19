/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * SoldItem remote interface
 * @author kim
 */
public interface SoldItem extends Remote {
    public Item getItem() throws RemoteException;
    public String getSeller() throws RemoteException;
    public String getBuyer() throws RemoteException;
    public void BuyerLeft() throws RemoteException;
    public void SellerLeft() throws RemoteException;

}
