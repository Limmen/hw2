/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import limmen.hw2.client.view.GuiController.WishListener;
import limmen.hw2.marketplace.model.Wish;
import net.miginfocom.swing.MigLayout;

/**
 * Wish-panel
 * @author kim
 */
public class WishPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    private final JTable table;
    private DefaultTableModel model = new DefaultTableModel();
    private final String[] columnNames;
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
        lbl = new JLabel("Item-price: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gaptop 10");
        JTextField priceField = new JTextField(25);
        priceField.setFont(Plain);
        add(priceField, "span 1, gaptop 10");
        JButton wishButton = new JButton ("Place wish");
        wishButton.setFont(Title);
        wishButton.addActionListener(contr.new WishListener(nameField, priceField));
        add(wishButton, "span 2");
        lbl = new JLabel("Your wishes are listed below, select one and click 'remove' to remove a wish");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        columnNames = new String[2];
        columnNames[0] = "Name";
        columnNames[1] = "Price";
        String[][] rowData = new String[0][0];
        model = new DefaultTableModel(rowData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(20);
        table.setFont(Plain);        
        table.getTableHeader().setFont(PBold);
        add(new JScrollPane(table), "span 2, gaptop 20");  
        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(contr.new removeWishListener(table));
        removeButton.setFont(Plain);
        add(removeButton, "span 2, gaptop 10");
    }
    public void updateWishes(ArrayList<Wish> wishes){
        String[][] rowData = new String[wishes.size()][2];
        try{
            for(int i = 0; i <  wishes.size(); i++)
            {
                Wish w  = wishes.get(i);
                rowData[i][0] = w.getItem().getName();
                rowData[i][1] = Float.toString(w.getItem().getPrice());
            }
            model.setDataVector(rowData, columnNames);
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        repaint();
        revalidate();
    }
}
