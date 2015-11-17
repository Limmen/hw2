/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.hw2.bank.Bank;
import limmen.hw2.client.view.GuiController;
import limmen.hw2.client.util.BankCommand;
import limmen.hw2.client.util.RejectedException;

/**
 *
 * @author kim
 */
public class BankWorker extends SwingWorker <Boolean, Boolean> {
    private final Bank bankobj;
    private final Client client;
    private final BankCommand command;
    private final GuiController contr;
    
    public BankWorker(Bank bankobj, Client client, BankCommand command, GuiController contr) {
        this.client = client;
        this.bankobj= bankobj;
        this.command = command;
        this.contr = contr;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
        switch(command.getCommandName()){
            case newAccount:
                newAccount();
                break;
            case getAccount:
                getAccount();
                break;
            case deleteAccount:
                deleteAccount();
                break;
            case withdraw:
                withdraw();
                break;
            case deposit:
                deposit();
                break;
            case balance:
                balance();
                break;
            case quit:
                quit();
                break;
            case help:
                help();
                break;
            case list:
                list();
                break;
        }
        return true;
    }
    
    private void newAccount(){
        try{
            client.setAccount(bankobj.newAccount(client.getName()));
            contr.updateLog(client.getName() + " created a new bankaccount at:  " + contr.getBankName());
        }
        catch(RejectedException e){
            e.printStackTrace();
        }
        catch(RemoteException e2){
            e2.printStackTrace();
        }
    }
    private void getAccount(){
        
    }
    private void deleteAccount(){
        try{
            bankobj.deleteAccount(client.getName());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void withdraw(){
        try{
            client.getAccount().withdraw(command.getAmount());
            contr.updateBalance();
            contr.updateLog(client.getName() + " withdraw " + command.getAmount());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        catch(RejectedException e2){
            
        }
    }
    private void deposit(){
        try{
            client.getAccount().deposit(command.getAmount());
            contr.updateBalance();
            contr.updateLog(client.getName() + " deposited " + command.getAmount());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        catch(RejectedException e2){
            
        }
    }
    private void balance(){
        
    }
    private void quit(){
        
    }
    private void help(){
        
    }
    private void list(){
        
    }
    
    
}
