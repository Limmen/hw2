/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import limmen.hw2.bank.Bank;
import limmen.hw2.client.model.BankWorker;
import limmen.hw2.client.model.Client;
import limmen.hw2.client.model.ClientImpl;
import limmen.hw2.client.model.MarketWorker;
import limmen.hw2.client.util.BankCommand;
import limmen.hw2.client.util.BankCommandName;
import limmen.hw2.client.util.MarketCommand;
import limmen.hw2.client.util.MarketCommandName;
import limmen.hw2.marketplace.ListedItem;
import limmen.hw2.marketplace.MarketPlace;

/**
 *
 * @author kim
 */
public class GuiController {
    private static final String DEFAULT_BANK_NAME = "Nordea";
    private static final String DEFAULT_MARKET_NAME = "ID2212_Buy_and_Sell";
    private final GuiController contr = this;
    private RegisterFrame registerFrame;
    private MainFrame mainFrame;
    private Bank bankobj;
    private MarketPlace marketobj;
    private Client client;
    private ArrayList<String> log = new ArrayList();
    public GuiController(){
        connectToBank();
        connectToMarketPlace();
        registerFrame = new RegisterFrame(contr);
    }
    
    public static void main(String[] args){
        new GuiController();
    }
    public String getBankName(){
        return this.DEFAULT_BANK_NAME;
    }
    public String getMarketName(){
        return this.DEFAULT_MARKET_NAME;
    }
    public Client getClient(){
        return client;
    }
    public void updateWishes(ArrayList<String> wishes){
        mainFrame.updateWishes(wishes);
    }    
    public void updateWishes(){
        new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.getWishes, client), contr).execute();
    }
    public void updateItems(ArrayList<ListedItem> items){
        mainFrame.updateItems(items);
    }
    public void updateItems(){
        new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.listItems, client), contr).execute();
    }
    public void updateForSale(ArrayList<ListedItem> items){
        mainFrame.updateForSale(items);
    }
    public void updateForSale(){
        new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.getForSale, client), contr).execute();
    }
    public void remoteExceptionHandler(){
        JOptionPane.showMessageDialog(null, "There was an error"
                    + " with the connection to the marketplace",
                    "ConnectionError", JOptionPane.INFORMATION_MESSAGE);
    }
    public void updateLog(String s){
        log.add(s);
    }
    public ArrayList<String> getLog(){
        return log;
    }
    private void connectToBank(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            bankobj = (Bank) Naming.lookup(DEFAULT_BANK_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to bank: " + DEFAULT_BANK_NAME);
    }
    private void connectToMarketPlace(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            marketobj = (MarketPlace) Naming.lookup(DEFAULT_MARKET_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to marketplace: " + DEFAULT_MARKET_NAME);
    }
    
    class RegisterListener implements ActionListener {
        private final JTextField nameField;
        
        RegisterListener(JTextField nameField){
            this.nameField = nameField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0){
                try{
                    client = new ClientImpl(nameField.getText());}
                catch(RemoteException remoteExc){
                    remoteExc.printStackTrace();
                }
                new BankWorker(bankobj, client, new BankCommand(BankCommandName.newAccount), contr).execute();
                new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.register, client), contr).execute();
                registerFrame.setVisible(false);                
                mainFrame = new MainFrame(contr, client);
            }
            else
                JOptionPane.showMessageDialog(null, "Your  username is"
                        + " to short or contains invalid characters",
                        "Invalid username", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
        }
    }
    
    class DeRegisterListener implements ActionListener {
        
        DeRegisterListener(){
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    class WishListener implements ActionListener {
        private final JTextField nameField;
        
        WishListener(JTextField nameField){
            this.nameField = nameField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0){                
                new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.wish, client,nameField.getText()), contr).execute();
            }
            else
                JOptionPane.showMessageDialog(null, "Item-name is not valid"
                        + "it needs to be atleast 1 letter",
                        "Invalid wish", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
        }
    }
    class SellListener implements ActionListener {
        private final JTextField nameField;
        private final JTextField descrField;
        private final JTextField priceField;
        
        SellListener(JTextField nameField, JTextField descrField, JTextField priceField){
            this.nameField = nameField;
            this.descrField = descrField;
            this.priceField = priceField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0 && descrField.getText().length()> 0 && priceField.getText().length() > 0){
                try{
                    new MarketWorker(marketobj, client, new MarketCommand(MarketCommandName.sell, client, nameField.getText(),
                    descrField.getText(), Float.parseFloat(priceField.getText())), contr).execute();
                }
                catch(NumberFormatException formatExc){
                    JOptionPane.showMessageDialog(null, "price need to be a valid number",
                            "Invalid price", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(null, "The information you have"
                        + "entered is not valid",
                        "Invalid item", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
            descrField.setText("");
            priceField.setText("");
        }
    }
}
