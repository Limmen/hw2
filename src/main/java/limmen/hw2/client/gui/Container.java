/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.gui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import limmen.hw2.client.model.Client;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class Container extends JPanel{
    GuiController contr;
    JPanel mainPanel;    
    public Container(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));
        add(new MainPanel(contr), "span 1");
    }
    public void transitionToBank(){
        removeAll();
        try{
            add(new BankPanel(contr), "span 1");
        }
        catch(RemoteException r){
            JOptionPane.showMessageDialog(null, "Sorry could'nt"
                    + "show your bankaccount at this moment"
                    + "try again later",
                    "Lost connection", JOptionPane.INFORMATION_MESSAGE);
            add(new MainPanel(contr), "span 1");
        }
        catch(NullPointerException r){
            JOptionPane.showMessageDialog(null, "Sorry we're"
                    + "still in the process of setting up your bank-account."
                    + "try again later",
                    "Account not finnished", JOptionPane.INFORMATION_MESSAGE);
            add(new MainPanel(contr), "span 1");
        }
    }
    public void transitionToFrontPage(){
        removeAll();
        add(new MainPanel(contr), "span 1");
    }
    public void transitionToBuy(){
        removeAll();
        add(new BuyPanel(contr), "span 1");
    }
    public void transitionToSell(){
        removeAll();
        add(new SellPanel(contr), "span 1");
    }
    public void transitionToWish(){
        removeAll();
        add(new WishPanel(contr), "span 1");
    }

}