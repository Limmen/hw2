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
import javax.swing.table.DefaultTableModel;
import limmen.hw2.marketplace.ListedItem;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class BuyPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    GuiController contr;
    DefaultTableModel model;
    String[] columnNames;
    JLabel balance;
    public BuyPanel(GuiController contr) throws RemoteException{
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("Balance: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        updateBalance();
        balance.setFont(Plain);
        add(balance, "span 1");
        lbl = new JLabel("Select a item you want to buy");
        lbl.setFont(Title);
        add(lbl, "span 2");
        columnNames = new String[4];
        columnNames[0] = "Name";
        columnNames[1] = "Description";
        columnNames[2] = "Price";
        columnNames[3] = "Seller";
        String[][] rowData = new String[0][0];
        model = new DefaultTableModel(rowData,columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(20);
        table.setFont(Plain);        
        table.getTableHeader().setFont(PBold);
        add(new JScrollPane(table), "span 2, gaptop 20");
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(Title);
        buyButton.addActionListener(contr.new buyListener(table));
        add(buyButton, "span 2, gaptop 10");
    }
    
    public void updateItems(ArrayList<ListedItem> items) throws RemoteException{        
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
                e.printStackTrace();
                contr.remoteExceptionHandler(e);
            }
        }
        model.setDataVector(rowData, columnNames);
        updateBalance();
        repaint();
        revalidate();
    }
    private void updateBalance() throws RemoteException{
        try{
            balance = new JLabel(Float.toString(contr.getClient().getAccount().getBalance()));
        }
        catch(NullPointerException e){
            balance = new JLabel("You dont have a valid account set");
        }
    }
    
    
}
