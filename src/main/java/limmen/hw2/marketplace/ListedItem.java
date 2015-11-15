/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace;

import limmen.hw2.client.model.Client;

/**
 *
 * @author kim
 */
public class ListedItem {
    
    Item item;
    Client seller;
    
    public ListedItem(Item item, Client seller){
        this.item = item;
        this.seller = seller;
    }
    
    Item getItem(){
        return item;
    }
    
    Client getSeller(){
        return seller;
    }
    
    
}
