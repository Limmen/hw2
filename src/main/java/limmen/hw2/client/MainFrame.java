/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    
    public MainFrame(GuiController contr, Client client){
        this.contr = contr;
        this.client = client;
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 2 ID2212 |Marketplace");
        this.setJMenuBar(createMenu());
        this.setContentPane(new JScrollPane(new Container()));
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
        JMenu menu = new JMenu("Menu Label");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Item Label");
        menu.add(item);
        return menuBar;
    }
    
    private class Container extends JPanel{
        
        Container(){
            setLayout(new MigLayout("wrap 2, insets 50 50 50 50"));
            add(new MainPanel(), "span 2");
        }
    }
    
    private class MainPanel extends JPanel{
        JTextArea log;
        
        MainPanel(){
            setLayout(new MigLayout("wrap 1"));
            JLabel lbl = new JLabel("Marketplace. User: " + client.getName());
            lbl.setFont(Title);
            add(lbl);
            JButton deRegButton = new JButton("DeRegister");
            deRegButton.setFont(Title);
            deRegButton.addActionListener(contr.new DeRegisterListener());
            add(deRegButton, "span 2, gaptop 15"); 
            lbl = new JLabel("Marketplace history");
            lbl.setFont(Plain);
            add(lbl, "span 2, gaptop 20");
            log = new JTextArea("");
            log.setLineWrap(true);
            log.setEditable(false);
            log.setFont(Plain);
            JScrollPane logPane = new JScrollPane(log);
            logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            logPane.setPreferredSize(new Dimension(400, 250));
            add(logPane, "span 2, gaptop 20");
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    try {
                        log.setText("");
                    }
                    catch(Exception e)
                    {
                        
                    }
                    
                }
            });
            clearButton.setFont(Plain);
            add(clearButton, "span 2, gaptop 10");
        }
    }
    
    private class BuyPanel extends JPanel{
        
    }
    
    private class SellPanel extends JPanel{
        
    }
    
    private class WishPanel extends JPanel{
        
    }
    
}
