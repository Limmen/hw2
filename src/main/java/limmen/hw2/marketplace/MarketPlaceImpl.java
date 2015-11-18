/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.hw2.client.model.Client;
import limmen.hw2.client.util.RejectedException;

/**
 *
 * @author kim
 */
public class MarketPlaceImpl extends UnicastRemoteObject implements MarketPlace {
    
    private final String marketName;
    private final ArrayList<Client> clients = new ArrayList();
    private ArrayList<ListedItem> listedItems = new ArrayList();
    private ArrayList<Wish> wishes = new ArrayList();
    
    public MarketPlaceImpl(String marketName) throws RemoteException{
        this.marketName = marketName;
    }
    
    @Override
    public void Buy(String name, String descr, float price, String seller, Client client) throws RemoteException, RejectedException {
        ArrayList<ListedItem> l = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getItem().getName().equals(name) &&
                    i.getItem().getDescription().equals(descr) &&
                    i.getItem().getPrice() == price &&
                    i.getSeller().getName().equals(seller)){
                client.getAccount().withdraw(price);
                i.getSeller().getAccount().deposit(price);
                i.getSeller().itemNotification(i.getItem().getName(), i.getItem().getPrice(), client);
            }
            else
                l.add(i);
        }
        listedItems = l;
    }
    @Override
    public void Sell(String name, String descr, float price, Client client) throws RemoteException {
        for(Wish i : wishes){
            if(i.getName().equals(name) && price <= i.getPrice()){
                i.getClient().wishNotification(name, price);
            }
        }
        listedItems.add(new ListedItemImpl(new ItemImpl(name,descr,price), client));
    }
    
    @Override
    public void register(Client client) throws RemoteException, RejectedException {
        boolean bool = true;
        for(Client cli : clients){
            if(cli.equals(client)){
                bool = false;
                throw new RejectedException("Rejected: " + this.getClass()
                        + marketName
                        + "You are already registered ");
                
            }if(cli.getName().equals(client.getName())){
                bool = false;
                throw new RejectedException("Rejected: " + this.getClass()
                        + marketName
                        + "A client with that name is already registered"
                        + "at the marketplace");
            }
        }
        if(bool)
            clients.add(client);
    }
    
    @Override
    public void deRegister(Client client) throws RemoteException {
        ArrayList<ListedItem> updItems = new ArrayList();
        ArrayList<Wish> updWishes = new ArrayList();
        if(clients.contains(client))
            clients.remove(client);
        for(ListedItem i : listedItems){
            if(!i.getSeller().equals(client))
                updItems.add(i);
        }
        for(Wish j : wishes){
            if(!j.getClient().equals(client))
                updWishes.add(j);
        }
        listedItems = updItems;
        wishes = updWishes;
    }
    
    @Override
    public ArrayList<ListedItem> listItems() throws RemoteException {
        return listedItems;
    }
    
    @Override
    public void wish(String name, float price,  Client client) throws RemoteException {
        wishes.add(new WishImpl(name , price, client));
    }
    
    @Override
    public ArrayList<Client> listClients() throws RemoteException {
        return clients;
    }
    
    @Override
    public ArrayList<Wish> getWishes(Client client) throws RemoteException {
        ArrayList<Wish> clientwishes = new ArrayList();
        for(Wish w : wishes){
            if(w.getClient().equals(client))
                clientwishes.add(w);
            
        }
        return clientwishes;
    }
    
    @Override
    public ArrayList<ListedItem> getForSale(Client client) throws RemoteException {
        ArrayList<ListedItem> forSale = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getSeller().equals(client))
                forSale.add(i);
        }
        return forSale;
    }
    
    @Override
    public void removeWish(String name, float price, Client client) throws RemoteException {
        ArrayList<Wish> updWishes = new ArrayList();
        for(Wish i : wishes){
            if(!i.getClient().equals(client) && i.getName().equals(name)
                    && i.getPrice() == price)
                updWishes.add(i);
        }
        wishes = updWishes;
    }
    
    @Override
    public void removeSell(String name, String descr, float price, Client client) throws RemoteException {
        ArrayList<ListedItem> items = new ArrayList();
        for(ListedItem i : listedItems){
            if(!i.getItem().getName().equals(name) &&
                    i.getItem().getDescription().equals(descr) &&
                    i.getItem().getPrice() == price &&
                    i.getSeller().equals(client))
                items.add(i);
        }
        listedItems = items;
    }
    
}
