/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
                client = new ClientImpl(nameField.getText());
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
        }
    }
}
