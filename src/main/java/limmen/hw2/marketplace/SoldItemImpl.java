/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * SoldItem-implementation. extends UniCastRemoteObject to automaticly 
 * export the remote object.
 * @author kim
 */
public class SoldItemImpl extends UnicastRemoteObject implements SoldItem {
    private String seller;
    private String buyer;
    private final Item item;
    
    public SoldItemImpl(String seller, String buyer, Item item) throws RemoteException{
        this.seller = seller;
        this.buyer = buyer;
        this.item = item;
    }
    @Override
    public String getSeller() throws RemoteException{
        return seller;
    }
    @Override
    public String getBuyer() throws RemoteException{
        return buyer;
    }
    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void BuyerLeft() throws RemoteException {
        buyer = "Inactive user";
    }

    @Override
    public void SellerLeft() throws RemoteException {
        seller = "Inactive user";
    }
    
}
