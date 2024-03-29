/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import limmen.hw2.marketplace.model.ListedItem;
import limmen.hw2.marketplace.model.SoldItem;
import limmen.hw2.marketplace.model.Wish;
import net.miginfocom.swing.MigLayout;

/**
 * The mainframe which represents a clients connection to the marketplace/bank
 * @author kim
 */
public class MainFrame extends JFrame {
    private final GuiController contr;
    private final Container container;
    
    public MainFrame(GuiController contr){
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 2 ID2212 |Marketplace");
        this.setJMenuBar(createMenu());
        container = new Container(contr);
        this.setContentPane(new JScrollPane(container));
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                dispose();
                deRegister();
                System.exit(0);
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
        contr.updateLog();
        contr.updateBought();
        contr.updateSold();
    }
    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Home");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToFrontPage();
                    contr.updateLog();
                    contr.updateBought();
                    contr.updateSold();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("Bank Account");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToBank();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("Buy");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToBuy();
                    contr.updateItems();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("Sell");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToSell();
                    contr.updateForSale();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("Wish");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToWish();
                    contr.updateWishes();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        return menuBar;
    }
    private void deRegister(){
        contr.logOut();
    }
    public void updateWishes(ArrayList<Wish> wishes){
        container.updateWishes(wishes);
    }
    public void updateItems(ArrayList<ListedItem> items){
        container.updateItems(items);
    }
    public void updateForSale(ArrayList<ListedItem> items){
        container.updateForSale(items);
    }
    public void updateBalance(){
        container.updateBalance();
    }
    public void updateLog(ArrayList<String> log){
        container.updateLog(log);
    }
    public void updateBought(ArrayList<SoldItem> soldItems){
        container.updateBought(soldItems);
    }
    public void updateSold(ArrayList<SoldItem> soldItems){
        container.updateSold(soldItems);
    }
    
}
