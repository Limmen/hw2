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
    private Item item;
    private MarketCommandName command;
    
    public MarketCommand(MarketCommandName command, Client client, Item item) {
        this.command = command;
        this.client = client;
        this.item = item;
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
    public Item getItem() {
        return item;
    }
}
