/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import limmen.hw2.client.Client;

/**
 *
 * @author kim
 */
public class Wish {
    private final Client client;
    private final Item item;
    
    public Wish(Item item, Client client){
        this.client = client;
        this.item = item;
    }

    public Client getClient() {
        return client;
    }

    public Item getItem() {
        return item;
    }
    
}
