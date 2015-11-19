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
 * Marketplace implementation. extends UniCastRemoteObject to automaticly 
 * export the remote object.
 * Methods are declared synchronized to get some thread-safety (only one
 * thread is allowed to execute the methods at a time, the rest of the threads
 * get queued up until it's their turn.
 * Shared resources like clients, listedItems and wishes are declared volatile
 * to avoid inconsistent reads.
 * @author kim
 */
public class MarketPlaceImpl extends UnicastRemoteObject implements MarketPlace {
    
    private final String marketName;
    private volatile ArrayList<Client> clients = new ArrayList();
    private volatile ArrayList<ListedItem> listedItems = new ArrayList();
    private volatile ArrayList<Wish> wishes = new ArrayList();
    private volatile ArrayList<SoldItem> sold = new ArrayList();
    
    public MarketPlaceImpl(String marketName) throws RemoteException{
        this.marketName = marketName;
    }
    
    @Override
    public synchronized void Buy(String name, String descr, float price, String seller, Client client) throws RemoteException, RejectedException {
        ArrayList<ListedItem> l = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getItem().getName().equals(name) &&
                    i.getItem().getDescription().equals(descr) &&
                    i.getItem().getPrice() == price &&
                    i.getSeller().getName().equals(seller)){
                client.getAccount().withdraw(price);
                i.getSeller().getAccount().deposit(price);
                i.getSeller().itemNotification(i.getItem().getName(), i.getItem().getPrice(), client);
                sold.add(new SoldItemImpl(i.getSeller().getName(), client.getName(), i.getItem()));
            }
            else
                l.add(i);
        }
        listedItems = l;
    }
    @Override
    public synchronized void Sell(String name, String descr, float price, Client client) throws RemoteException {
        for(Wish i : wishes){
            if(i.getName().equals(name) && price <= i.getPrice()){
                i.getClient().wishNotification(name, price);
            }
        }
        listedItems.add(new ListedItemImpl(new ItemImpl(name,descr,price), client));
    }
    
    @Override
    public synchronized void register(Client client) throws RemoteException, RejectedException {
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
    public synchronized void deRegister(Client client) throws RemoteException {
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
        for(SoldItem k : sold){
            if(k.getSeller().equals(client.getName()))
                k.SellerLeft();
            if(k.getBuyer().equals(client.getName()))
                k.BuyerLeft();
        }
        listedItems = updItems;
        wishes = updWishes;
    }
    
    @Override
    public synchronized ArrayList<ListedItem> listItems() throws RemoteException {
        return listedItems;
    }
    
    @Override
    public synchronized void wish(String name, float price,  Client client) throws RemoteException {
        wishes.add(new WishImpl(name , price, client));
    }
    
    @Override
    public synchronized ArrayList<Client> listClients() throws RemoteException {
        return clients;
    }
    
    @Override
    public synchronized ArrayList<Wish> getWishes(Client client) throws RemoteException {
        ArrayList<Wish> clientwishes = new ArrayList();
        for(Wish w : wishes){
            if(w.getClient().equals(client))
                clientwishes.add(w);
            
        }
        return clientwishes;
    }
    
    @Override
    public synchronized ArrayList<ListedItem> getForSale(Client client) throws RemoteException {
        ArrayList<ListedItem> forSale = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getSeller().equals(client))
                forSale.add(i);
        }
        return forSale;
    }
    
    @Override
    public synchronized void removeWish(String name, float price, Client client) throws RemoteException {
        ArrayList<Wish> updWishes = new ArrayList();
        for(Wish i : wishes){
            if(!i.getClient().equals(client) && i.getName().equals(name)
                    && i.getPrice() == price)
                updWishes.add(i);
        }
        wishes = updWishes;
    }
    
    @Override
    public synchronized void removeSell(String name, String descr, float price, Client client) throws RemoteException {
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

    @Override
    public synchronized ArrayList<SoldItem> getBought(Client client) throws RemoteException {
        ArrayList<SoldItem> bought = new ArrayList();
        for(SoldItem i : sold){
            if(i.getBuyer().equals(client.getName()))
                bought.add(i);
        }
        return bought;
    }

    @Override
    public synchronized ArrayList<SoldItem> getSold(Client client) throws RemoteException {
        ArrayList<SoldItem> clientSold = new ArrayList();
        for(SoldItem i : sold){
            if(i.getSeller().equals(client.getName()))
                clientSold.add(i);
        }
        return clientSold;
    }    
}
