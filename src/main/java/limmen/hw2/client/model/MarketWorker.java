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
import limmen.hw2.marketplace.model.MarketPlace;

/**
 * Marketworker class. This class does remote method invocation on the
 * marketplace remote-interface.
 * @author kim
 */
public class MarketWorker extends SwingWorker<Boolean,Boolean> {
    private final MarketPlace marketobj;
    private final Client client;
    private final MarketCommand command;
    private final GuiController contr;
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
            case logout:
                logOut();
                break;
            case login:
                logIn();
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
            case removeWish:
                removeWish();
                break;
            case removeSell:
                removeSell();
                break;
            case getSold:
                getSold();
                break;
            case getBought:
                getBought();
                break;
        }
        return true;
    }
    
    private void buy(){
        try{
            marketobj.Buy(command.getItemName(),command.getItemDescr(),
                    command.getPrice(),command.getSeller(),client);
            contr.updateLog("you bought " +
                    command.getItemName() + " from " + command.getSeller() +
                    " for: " + command.getPrice());
            listItems();
        }
        catch(RejectedException e2){
            contr.rejectedExceptionHandler(e2);
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
            marketobj.wish(command.getItemName(), command.getPrice(), client);
            contr.updateLog(client.getName() + " made a wish for: " + command.getItemName());
            getWishes();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void register(){
        System.out.println("registering");
        try{
            marketobj.register(client, command.getPassword());
            contr.successfulReg();
            //contr.updateLog(client.getName() + " registered at the marketplace");
        }
        catch(RejectedException e){
            contr.failedReg();
        }
        catch(RemoteException e2){
            contr.remoteExceptionHandler(e2);
        }
    }
    private void logOut(){
        try{
            marketobj.logOut(command.getClient());
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
    private void removeWish(){
        try{
            marketobj.removeWish(command.getItemName(), command.getPrice(), command.getClient());
            contr.updateLog(client.getName() + " removed wish for " + command.getItemName());
            getWishes();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void removeSell(){
        try{
            marketobj.removeSell(command.getItemName(), command.getItemDescr(), command.getPrice(), command.getClient());
            contr.updateLog(client.getName() + " removed " + command.getItemName() +
                    " that was listed as for sale");
            getForSale();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void getBought(){
        try{
            contr.updateBought(marketobj.getBought(client));
            getForSale();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void getSold(){
        try{
            contr.updateSold(marketobj.getSold(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void logIn(){
        
        try{
            if(marketobj.login(client, command.getPassword())){
                contr.setAccount();
                contr.successfulLogin();   
            }
            else
                contr.failedLogin();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
