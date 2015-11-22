/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.marketplace.model;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.hw2.bank.Bank;
import limmen.hw2.client.model.Client;
import limmen.hw2.client.util.RejectedException;
import limmen.hw2.marketplace.integration.DBhandler;
import limmen.hw2.marketplace.integration.QueryManager;

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
    private static final String DEFAULT_BANK_NAME = "Nordea";
    private final String marketName;
    private volatile ArrayList<Client> loggedInClients = new ArrayList();
    private DBhandler db;
    private QueryManager qm;
    private Bank bankobj;
    
    public MarketPlaceImpl(String marketName) throws RemoteException{
        this.marketName = marketName;
        db = new DBhandler();
        qm = new QueryManager(db);
        connectToBank();
    }
    
    @Override
    public synchronized void Buy(String name, String descr, float price, String seller, Client client) throws RemoteException, RejectedException {
        ArrayList<ListedItem> listedItems = qm.getListedItems();
        ArrayList<ListedItem> l = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getItem().getName().equals(name) &&
                    i.getItem().getDescription().equals(descr) &&
                    i.getItem().getPrice() == price &&
                    i.getSeller().equals(seller)){
                client.getAccount().withdraw(price);
                bankobj.getAccount(seller).deposit(price);
                Client c = getClient(seller);
                if(c != null)
                    c.itemNotification(i.getItem().getName(), i.getItem().getPrice(), client);
                qm.newSoldItem(client.getName(), seller, i.getItem().getId());
            }
            else
                l.add(i);
        }
        listedItems = l;
    }
    @Override
    public synchronized void Sell(String name, String descr, float price, Client client) throws RemoteException {
        ArrayList<Wish> wishes = qm.getWishes();
        for(Wish i : wishes){
            if(i.getItem().getName().equals(name) && price <= i.getItem().getPrice()){
                Client wisher = getClient(i.getUser());
                if(wisher != null)
                    wisher.wishNotification(name, price);
            }
        }
        qm.newListedItem(name, descr, price, client.getName());        
    }
    
    @Override
    public synchronized void register(Client client, String pw) throws RemoteException, RejectedException {
        boolean bool = true;
        ArrayList<String> users = qm.getUsers();
        for(String user : users){
            if(user.equals(client.getName())){
                bool = false;
                throw new RejectedException("Rejected: " + this.getClass()
                        + marketName
                        + "That username is taken ");
            }
        }
        if(bool)
           qm.newUser(client.getName(), pw);
    }
    
    @Override
    public synchronized void logOut(Client client) throws RemoteException {
        if(loggedInClients.contains(client))
            loggedInClients.remove(client);        
    }
    
    @Override
    public synchronized ArrayList<ListedItem> listItems() throws RemoteException {
        return qm.getListedItems();
    }
    
    @Override
    public synchronized void wish(String name, float price,  Client client) throws RemoteException {
        qm.newWish(name, price, client.getName());
    }
  /*  
    @Override
    public synchronized ArrayList<Client> listClients() throws RemoteException {
        return clients;
    }
   */ 
    @Override
    public synchronized ArrayList<Wish> getWishes(Client client) throws RemoteException {
        ArrayList<Wish> wishes = qm.getWishes();
        ArrayList<Wish> clientwishes = new ArrayList();
        for(Wish w : wishes){
            if(w.getUser().equals(client.getName()))
                clientwishes.add(w);
            
        }
        return clientwishes;
    }
    
    @Override
    public synchronized ArrayList<ListedItem> getForSale(Client client) throws RemoteException {
        ArrayList<ListedItem> listedItems = qm.getListedItems();
        ArrayList<ListedItem> forSale = new ArrayList();
        for(ListedItem i : listedItems){
            if(i.getSeller().equals(client.getName()))
                forSale.add(i);
        }
        return forSale;
    }
    
    @Override
    public synchronized void removeWish(String name, float price, Client client) throws RemoteException {
        qm.removeWish(name, price, client.getName());

    }
    
    @Override
    public synchronized void removeSell(String name, String descr, float price, Client client) throws RemoteException {
        qm.removeListedItem(name, descr, price, client.getName());
    }
    
    @Override
    public synchronized ArrayList<SoldItem> getBought(Client client) throws RemoteException {
        ArrayList<SoldItem> sold = qm.getSold();
        ArrayList<SoldItem> bought = new ArrayList();
        for(SoldItem i : sold){
            if(i.getBuyer().equals(client.getName()))
                bought.add(i);
        }
        return bought;
    }
    
    @Override
    public synchronized ArrayList<SoldItem> getSold(Client client) throws RemoteException {
        ArrayList<SoldItem> sold = qm.getSold();
        ArrayList<SoldItem> clientSold = new ArrayList();
        for(SoldItem i : sold){
            if(i.getSeller().equals(client.getName()))
                clientSold.add(i);
        }
        return clientSold;
    }
    
    private Client getClient(String name) throws RemoteException{
        for(Client i : loggedInClients){
            if(i.getName().equals(name))
                return i;
        }
        return null;
    }
    private void connectToBank(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            bankobj = (Bank) Naming.lookup(DEFAULT_BANK_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to bank: " + DEFAULT_BANK_NAME);
    }

    @Override
    public boolean login(Client client, String password) throws RemoteException {
        String pw = qm.getPassword(client.getName());
        if(pw != null){
            if(pw.equals(password))
                return true;
        }
        return false;
    }
}
