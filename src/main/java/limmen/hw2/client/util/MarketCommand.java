/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.util;

import limmen.hw2.client.model.Client;
import limmen.hw2.marketplace.Item;

/**
 *
 * @author kim
 */
public class MarketCommand {
    private Client client;
    private MarketCommandName command;
    private String itemName = "";
    private String itemDescr = "";
    private float price = 0;
    
    
    public MarketCommand(MarketCommandName command, Client client, String itemName, String itemDescr, float price) {
        this.command = command;
        this.client = client;
        this.itemName = itemName;
        this.itemDescr = itemDescr;
        this.price = price;
    }
    public MarketCommand(MarketCommandName command, Client client, String itemName) {
        this.command = command;
        this.client = client;
        this.itemName = itemName;
    }
    public MarketCommand(MarketCommandName command, Client client) {
        this.command = command;
        this.client = client;
    }
    public Client getClient() {
        return client;
    }    
    public MarketCommandName getCommandName() {
        return command;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public float getPrice() {
        return price;
    }
    
}
