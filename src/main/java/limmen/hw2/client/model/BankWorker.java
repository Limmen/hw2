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
            case getAccount:
                getAccount();
            case deleteAccount:
                deleteAccount();
            case withdraw:
                withdraw();
            case deposit:
                deposit();
            case balance:
                balance();
            case quit:
                quit();
            case help:
                help();
            case list:
                list();
        }
        return true;
    }
    
    private void newAccount(){
        try{
        client.setAccount(bankobj.newAccount(client.getName()));
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
        
    }
    private void withdraw(){
        
    }
    private void deposit(){
        
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
