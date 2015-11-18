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
import limmen.hw2.marketplace.ListedItem;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class SellPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    private final DefaultTableModel model;
    private final String[] columnNames;
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
        add(sellButton, "span 2, gaptop 20");
        columnNames = new String[4];
        columnNames[0] = "Name";
        columnNames[1] = "Description";
        columnNames[2] = "Price";
        columnNames[3] = "Seller";
        String[][] rowData = new String[0][0];
        lbl = new JLabel("<html> Items you have for sale on the market <br>"
                + " select a item"
                + " and click 'remove' to remove it from the marketplace </html>");
        lbl.setFont(Title);
        add(lbl, "span 2, gaptop 20");
        model = new DefaultTableModel(rowData,columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(20);
        table.setFont(Plain);
        table.getTableHeader().setFont(PBold);
        add(new JScrollPane(table), "span 2, gaptop 10");
        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(contr.new removeSellListener(table));
        removeButton.setFont(Plain);
        add(removeButton, "span 2, gaptop 10");
    }
    public void updateForSale(ArrayList<ListedItem> items){
        String[][] rowData = new String[items.size()][4];
        for(int i = 0; i <  items.size(); i++)
        {
            try{
                ListedItem item = items.get(i);
                rowData[i][0] = item.getItem().getName();
                rowData[i][1] = item.getItem().getDescription();
                rowData[i][2] = Float.toString(item.getItem().getPrice());
                rowData[i][3] = item.getSeller().getName();
            }
            catch(RemoteException e){
                contr.remoteExceptionHandler(e);
            }
        }
        model.setDataVector(rowData, columnNames);
        repaint();
        revalidate();
    }
}
