/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.view;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import limmen.hw2.client.view.GuiController.WishListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class WishPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    GuiController contr;
    DefaultListModel model = new DefaultListModel();
    public WishPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("<html> Wish item<br>"
                + "We will notify you if the item becomes listed on the <br>"
                + "marketplace </html>");
        lbl.setFont(Title);
        add(lbl, "span 2");
        lbl = new JLabel("Item-name: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gaptop 10");
        JTextField nameField = new JTextField(25);
        nameField.setFont(Plain);
        add(nameField, "span 1, gaptop 10");
        JButton wishButton = new JButton ("Place wish");
        wishButton.setFont(Title);
        wishButton.addActionListener(contr.new WishListener(nameField));
        add(wishButton, "span 2");
        lbl = new JLabel("Your wishes: ");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        add(new JScrollPane(new JList(model)), "span 2");
        
        
    }
    public void updateWishes(ArrayList<String> wishes){
        System.out.println("update wishes / panel, wishes size: " + wishes.size());
        model.removeAllElements();
        for(String str : wishes){
            model.addElement(str);
        }
        repaint();
        revalidate();
    }
}
