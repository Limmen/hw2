/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.hw2.client.Client;

/**
 *
 * @author kim
 */
public class MarketPlaceImpl implements MarketPlace {

    ArrayList<Client> clients;
    ArrayList<ListedItem> listedItems;
    ArrayList<Wish> wishes;
    
    @Override
    public Item Buy(Item item, Client client) throws RemoteException {
        for(ListedItem i : listedItems){
            if(i.getItem().equals(item)){
                //contact bank and delete item from listedItems,
                //also notify seller.
            }
        }
        return item;
            
    }

    @Override
    public void Sell(Item item, Client client) throws RemoteException {
        listedItems.add(new ListedItem(item, client));
    }

    @Override
    public void register(Client client) throws RemoteException {
        if(!clients.contains(client))
            clients.add(client);
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
    public void wish(Item item, Client client) throws RemoteException {
        wishes.add(new Wish(item , client));
    }
    
}
