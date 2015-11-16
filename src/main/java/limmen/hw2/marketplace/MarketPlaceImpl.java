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
    public void Buy(Item item, Client client) throws RemoteException {
        for(ListedItem i : listedItems){
            if(i.getItem().equals(item)){
                //contact bank and delete item from listedItems,
                //also notify seller.
            }
        }
        
    }
    
    @Override
    public void Sell(String name, String descr, float price, Client client) throws RemoteException {
        System.out.println("Adding item to listed items");
        listedItems.add(new ListedItemImpl(new ItemImpl(name,descr,price), client));
        System.out.println("listeditems size: " + listedItems.size());
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
        System.out.println("Client added?");
        System.out.println("clients size: " + clients.size());
    }
    
    @Override
    public void deregister(Client client) throws RemoteException {
        if(clients.contains(client))
            clients.remove(client);
    }
    
    @Override
    public ArrayList<ListedItem> listItems() throws RemoteException {
        return listedItems;
    }
    
    @Override
    public void wish(String name, Client client) throws RemoteException {
        System.out.println("Placing wish");
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
