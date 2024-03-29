/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import limmen.hw2.marketplace.model.ListedItem;
import limmen.hw2.marketplace.model.SoldItem;
import limmen.hw2.marketplace.model.Wish;
import net.miginfocom.swing.MigLayout;

/**
 * The contianer for the MainFrame
 * @author kim
 */
public class Container extends JPanel{
    private final GuiController contr;
    private MainPanel mainPanel;
    private BuyPanel buyPanel;
    private SellPanel sellPanel;
    private WishPanel wishPanel;
    private BankPanel bankPanel;
    public Container(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    public void transitionToBank(){
        removeAll();
        try{
            bankPanel = new BankPanel(contr);
            add(bankPanel, "span 1");
        }
        catch(RemoteException r){
            contr.remoteExceptionHandler(r);
            mainPanel = new MainPanel(contr);
            add(mainPanel, "span 1");
        }
        catch(NullPointerException r){
            JOptionPane.showMessageDialog(null, "Sorry we're"
                    + "still in the process of setting up your bank-account."
                    + "try again later",
                    "Account not finnished", JOptionPane.INFORMATION_MESSAGE);
            mainPanel = new MainPanel(contr);
            add(mainPanel, "span 1");
        }
    }
    public void transitionToFrontPage(){
        removeAll();
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    public void transitionToBuy(){
        removeAll();
        try{
            buyPanel = new BuyPanel(contr);
            add(buyPanel, "span 1");
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    public void transitionToSell(){
        removeAll();
        sellPanel = new SellPanel(contr);
        add(sellPanel, "span 1");
    }
    public void transitionToWish(){
        removeAll();
        wishPanel = new WishPanel(contr);
        add(wishPanel, "span 1");
    }
    
    public void updateWishes(ArrayList<Wish> wishes){
        if(wishPanel != null)
            wishPanel.updateWishes(wishes);
    }
    public void updateItems(ArrayList<ListedItem> items){
        if(buyPanel != null)
            buyPanel.updateItems(items);
    }
    public void updateForSale(ArrayList<ListedItem> items){
        if(sellPanel != null)
            sellPanel.updateForSale(items);
    }
    public void updateBalance(){
        if(bankPanel != null)
            bankPanel.updateBalance();
    }
    public void updateLog(ArrayList<String> log){
        if(mainPanel != null)
            mainPanel.updateLog(log);
    }
    public void updateBought(ArrayList<SoldItem> soldItems){
        if(mainPanel != null)
            mainPanel.updateBought(soldItems);
    }
    public void updateSold(ArrayList<SoldItem> soldItems){
        if(mainPanel != null)
            mainPanel.updateSold(soldItems);
    }
}