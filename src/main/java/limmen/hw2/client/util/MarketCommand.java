/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.util;

import limmen.hw2.client.model.Client;

/**
 * This class represents a command sent to the marketplace.
 * @author kim
 */
public class MarketCommand {
    private final Client client;
    private final MarketCommandName command;
    private String itemName = "";
    private String itemDescr = "";
    private float price = 0;
    private String seller = "";
    
    /* Constructor for buy-commands*/
    public MarketCommand(MarketCommandName command, Client client, String itemName, String itemDescr, float price, String seller) {
        this.command = command;
        this.client = client;
        this.itemName = itemName;
        this.itemDescr = itemDescr;
        this.price = price;
        this.seller = seller;
    }
    /* Constructor for sell-commands */
    public MarketCommand(MarketCommandName command, Client client, String itemName, String itemDescr, float price) {
        this.command = command;
        this.client = client;
        this.itemName = itemName;
        this.itemDescr = itemDescr;
        this.price = price;
    }
    /* Constructor for wish-command */
    public MarketCommand(MarketCommandName command, Client client, String itemName, float price) {
        this.command = command;
        this.client = client;
        this.itemName = itemName;
        this.price = price;
    }
    /* Constructor for get-commands */
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

    public String getSeller() {
        return seller;
    }
    
}
