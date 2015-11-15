/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.hw2.client.gui.GuiController;
import limmen.hw2.client.util.MarketCommand;
import limmen.hw2.client.util.RejectedException;
import limmen.hw2.marketplace.MarketPlace;

/**
 *
 * @author kim
 */
public class MarketWorker extends SwingWorker<Boolean,Boolean> {
    private MarketPlace marketobj;
    private Client client;
    private MarketCommand command;
    private GuiController contr;
    public MarketWorker(MarketPlace marketobj, Client client, MarketCommand command, GuiController contr) {
        this.client = client;
        this.marketobj= marketobj;
        this.command = command;
        this.contr = contr;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
        switch(command.getCommandName()){
            case buy:
                buy();
            case sell:
                sell();
            case wish:
                wish();
            case register:
                register();
            case deRegister:
                deRegister();
            case listItems:
                listItems();                                
        }
        return true;
    }
    
    private void buy(){
        
    }
    private void sell(){
        
    }
    private void wish(){
        
    }
    private void register(){
        try{
            marketobj.register(client);
        }
        catch(RejectedException e){
            
        }
        catch(RemoteException e2){
            
        }
    }
    private void deRegister(){
        
    }
    private void listItems(){
        
    }
}
