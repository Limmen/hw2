/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.RemoteException;

/**
 *
 * @author kim
 */
public class ItemImpl implements Item {
    private final int price;
    private final String name;
    private final String description;

    public ItemImpl(int price, String name, String description){
        this.price = price;
        this.name = name;
        this.description = description;
    }
    
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getDescription() throws RemoteException {
        return description;
    }

    @Override
    public int getPrice() throws RemoteException {
        return price;
    }
}
