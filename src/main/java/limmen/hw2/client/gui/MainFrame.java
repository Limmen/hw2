/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import limmen.hw2.client.model.Client;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainFrame extends JFrame {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    private final Client client;
    private final Container container;
    
    public MainFrame(GuiController contr, Client client){
        this.contr = contr;
        this.client = client;
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 2 ID2212 |Marketplace");
        this.setJMenuBar(createMenu());
        container = new Container(contr);
        this.setContentPane(new JScrollPane(container));
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
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
        return menuBar;
    }                       
    private class BuyPanel extends JPanel{
        
    }
    
    private class SellPanel extends JPanel{
        
    }
    
    private class WishPanel extends JPanel{
        
    }   
}
