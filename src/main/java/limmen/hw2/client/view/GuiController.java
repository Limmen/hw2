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
import javax.swing.JTable;
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
        new MarketWorker(marketobj,new MarketCommand(MarketCommandName.getWishes, client), contr).execute();
    }
    public void updateItems(ArrayList<ListedItem> items){
        mainFrame.updateItems(items);
    }
    public void updateItems(){
        new MarketWorker(marketobj,new MarketCommand(MarketCommandName.listItems, client), contr).execute();
    }
    public void updateForSale(ArrayList<ListedItem> items){
        mainFrame.updateForSale(items);
    }
    public void updateForSale(){
        new MarketWorker(marketobj,new MarketCommand(MarketCommandName.getForSale, client), contr).execute();
    }
    public void updateBalance(){
        mainFrame.updateBalance();
    }
    public void deRegister(){
        MarketWorker marketWorker = new MarketWorker(marketobj,new MarketCommand(MarketCommandName.deRegister, client), contr);
        BankWorker bankWorker = new BankWorker(bankobj,client, new BankCommand(BankCommandName.deleteAccount), contr);
        marketWorker.execute();
        bankWorker.execute();
        try{
            marketWorker.get();
            bankWorker.get();
        }
        catch(Exception e){
            
        }
    }
    public void remoteExceptionHandler(RemoteException e){
        System.out.println("remoteexceptionhandler!!!");
        e.printStackTrace();
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
                    client = new ClientImpl(nameField.getText(), contr);
                }
                catch(RemoteException remoteExc){
                    remoteExc.printStackTrace();
                }
                new BankWorker(bankobj, client, new BankCommand(BankCommandName.newAccount), contr).execute();
                new MarketWorker(marketobj,new MarketCommand(MarketCommandName.register, client), contr).execute();
                registerFrame.setVisible(false);
                mainFrame = new MainFrame(contr, client);
            }
            else{
                invalidInput();
            }
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
                new MarketWorker(marketobj,new MarketCommand(MarketCommandName.wish, client,nameField.getText()), contr).execute();
            }
            else{
                invalidInput();
            }
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
                    new MarketWorker(marketobj,new MarketCommand(MarketCommandName.sell, client, nameField.getText(),
                            descrField.getText(), Float.parseFloat(priceField.getText())), contr).execute();
                }
                catch(NumberFormatException formatExc){
                    invalidInput();
                }
            }
            else{
                invalidInput();
            }
            nameField.setText("");
            descrField.setText("");
            priceField.setText("");
        }
    }
    class withdrawListener implements ActionListener {
        private final JTextField withdrawField;
        
        withdrawListener(JTextField withdrawField){
            this.withdrawField = withdrawField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if(Float.parseFloat(withdrawField.getText()) <= client.getAccount().getBalance()){
                    if(withdrawField.getText().length() > 0){
                        
                        new BankWorker(bankobj, client, new BankCommand(BankCommandName.withdraw, Float.parseFloat(withdrawField.getText())), contr).execute();
                    }
                    else{
                        invalidInput();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "You don't have that"
                            + "amount of money to withdraw",
                            "Invalid number", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch(NumberFormatException s){
                invalidInput();
            }
            catch(RemoteException e2){
                contr.remoteExceptionHandler(e2);
            }
            withdrawField.setText("");
        }
    }
    class depositListener implements ActionListener {
        private final JTextField depositField;
        
        depositListener(JTextField depositField){
            this.depositField = depositField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(depositField.getText().length() > 0){
                try{
                    new BankWorker(bankobj, client, new BankCommand(BankCommandName.deposit, Float.parseFloat(depositField.getText())), contr).execute();
                }
                catch(NumberFormatException s){
                    invalidInput();
                }
            }
            else
            {
                invalidInput();
            }
            depositField.setText("");
        }
    }
    class buyListener implements ActionListener {
        private final JTable table;
        
        buyListener(JTable table){
            this.table = table;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            try{
            if((Float.parseFloat((String)table.getModel().getValueAt(row, 2)))
                    > client.getAccount().getBalance()){
                 JOptionPane.showMessageDialog(null, "You don't have enough money",
                            "Invalid transaction", JOptionPane.INFORMATION_MESSAGE);
                 return;
            }
            new MarketWorker(marketobj,
                    new MarketCommand(MarketCommandName.buy,client,
                            (String) table.getModel().getValueAt(row,0),
                            (String) table.getModel().getValueAt(row, 1),
                            Float.parseFloat((String) table.getModel().getValueAt(row, 2)),
                            (String) table.getModel().getValueAt(row, 3)),
                    contr).execute();            
            }
            catch(RemoteException e2){
                remoteExceptionHandler(e2);
            }
        }
        
    }
        
    
    public void invalidInput(){
        JOptionPane.showMessageDialog(null, "That is not valid input",
                "Invalid input", JOptionPane.INFORMATION_MESSAGE);
    }
}
