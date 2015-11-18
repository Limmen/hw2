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
    
    private String marketName;
    ArrayList<Client> clients = new ArrayList();
    ArrayList<ListedItem> listedItems = new ArrayList();
    ArrayList<Wish> wishes = new ArrayList();
    
    public MarketPlaceImpl(String marketName) throws RemoteException{
        this.marketName = marketName;
    }
    
    @Override
    public void Buy(String name, String descr, float price, String seller, Client client) throws RemoteException {
        ArrayList<ListedItem> l = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getItem().getName().equals(name) && 
                    i.getItem().getDescription().equals(descr) &&
                    i.getItem().getPrice() == price &&
                    i.getSeller().getName().equals(seller)){
                try{
                client.getAccount().withdraw(price);
                i.getSeller().getAccount().deposit(price);
                i.getSeller().itemNotification(i.getItem().getName(), i.getItem().getPrice(), client);
                }
                catch(RejectedException e){
                    e.printStackTrace();
                }                
            }
            else
                l.add(i);
        }
        listedItems = l;
    }
    @Override
    public void Sell(String name, String descr, float price, Client client) throws RemoteException {
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
        if(clients.contains(client))
            clients.remove(client);
        for(ListedItem i : listedItems){
            if(i.getSeller().equals(client))
                listedItems.remove(i);
        }
        for(Wish j : wishes){
            if(j.getClient().equals(client))
                wishes.remove(j);
        }
    }
    
    @Override
    public ArrayList<ListedItem> listItems() throws RemoteException {
        return listedItems;
    }
    
    @Override
    public void wish(String name, Client client) throws RemoteException {
        wishes.add(new Wish(new ItemImpl(name) , client));
    }

    @Override
    public ArrayList<Client> listClients() throws RemoteException {
        return clients;
    }

    @Override
    public ArrayList<String> getWishes(Client client) throws RemoteException {
        ArrayList<String> clientwishes = new ArrayList();
        for(Wish w : wishes){
            if(w.getClient().equals(client))
                clientwishes.add(w.getItem().getName());
                
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
    
}
