/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author kim
 */
public class ItemImpl extends UnicastRemoteObject implements Item {
    private final float price;
    private final String name;
    private final String description;

    public ItemImpl(String name, String description,float price) throws RemoteException{
        this.price = price;
        this.name = name;
        this.description = description;
    }
    public ItemImpl(String name) throws RemoteException{
        price = 0;
        description = "";
        this.name = name;
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
    public float getPrice() throws RemoteException {
        return price;
    }
    @Override
    public String toString(){
        return name + "\n" + description + "\n" + price;
    }
}
