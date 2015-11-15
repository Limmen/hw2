/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.gui;

import java.awt.Font;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class SellPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    GuiController contr;
    public SellPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("<html> List item for sale <br>"
                + "We will notify you if it gets sold<br></html>");
        lbl.setFont(Title);
        add(lbl, "span 2");
        lbl = new JLabel("Item name: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gaptop 10");
        JTextField nameField = new JTextField(25);
        nameField.setFont(Plain);
        add(nameField, "span 1, gaptop 10");
        lbl = new JLabel("Item description: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gaptop 10");
        JTextField descrField = new JTextField(25);
        descrField.setFont(Plain);
        add(descrField, "span 1, gaptop 10");
        lbl = new JLabel("Price:");
        lbl.setFont(PBold);
        add(lbl, "span 1, gaptop 10");
        JTextField priceField = new JTextField(25);
        priceField.setFont(Plain);
        add(priceField, "span 1, gaptop 10");
        JButton sellButton = new JButton ("List item for sale");
        sellButton.setFont(Title);
        sellButton.addActionListener(contr.new SellListener(nameField, descrField, priceField));
        add(sellButton);
    }
}
