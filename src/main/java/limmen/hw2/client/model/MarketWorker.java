/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.hw2.client.view.GuiController;
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
    public MarketWorker(MarketPlace marketobj,MarketCommand command, GuiController contr) {
        this.client = command.getClient();
        this.marketobj= marketobj;
        this.command = command;
        this.contr = contr;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
        switch(command.getCommandName()){
            case buy:
                buy();
                break;
            case sell:
                sell();
                break;
            case register:
                register();
                break;
            case wish:
                wish();
                break;
            case deRegister:
                deRegister();
                break;
            case listItems:
                listItems();
                break;
            case getWishes:
                getWishes();
                break;
            case getForSale:
                getForSale();
                break;
        }
        return true;
    }
    
    private void buy(){
        try{
            marketobj.Buy(command.getItemName(),command.getItemDescr(),
                    command.getPrice(),command.getSeller(),client);
            contr.updateLog(client.getName() + " bhougt " +
                    command.getItemName() + " from " + command.getSeller() +
                    " for: " + command.getPrice());
            listItems();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void sell(){
        try{
            marketobj.Sell(command.getItemName(),command.getItemDescr(),command.getPrice(),client);
            contr.updateLog(client.getName() + " listed " + command.getItemName() + " for sale");
            getForSale();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void wish(){
        try{
            marketobj.wish(command.getItemName(), client);
            contr.updateLog(client.getName() + " made a wish for: " + command.getItemName());
            getWishes();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void register(){
        try{
            marketobj.register(client);
            contr.updateLog(client.getName() + " registered at the marketplace");
        }
        catch(RejectedException e){
            e.printStackTrace();
        }
        catch(RemoteException e2){
            contr.remoteExceptionHandler(e2);
        }
    }
    private void deRegister(){
        try{
            marketobj.deRegister(command.getClient());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void listItems(){
        try{
            contr.updateItems(marketobj.listItems());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void getWishes(){
        try{
            contr.updateWishes(marketobj.getWishes(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void getForSale(){
        try{
            contr.updateForSale(marketobj.getForSale(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
