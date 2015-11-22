/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import limmen.hw2.marketplace.model.SoldItem;
import net.miginfocom.swing.MigLayout;

/**
 * MainPanel (start-screen when connected to the marketplace)
 * @author kim
 */
public class MainPanel extends JPanel{
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private JTextArea log;
    private final GuiController contr;
    private final DefaultTableModel soldModel;
    private final DefaultTableModel boughtModel;
    private final String[] columnNames;
    
    public MainPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel();
        try{
            lbl = new JLabel("<html> " + contr.getMarketName() + "<br>"
                    + "User: " + contr.getClient().getName() + "</html>");
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        lbl.setFont(Title);
        add(lbl, "span 2");
        JButton deRegButton = new JButton("log out");
        deRegButton.setFont(Title);
        deRegButton.addActionListener(contr.new logOutListener());
        add(deRegButton, "span 2, gaptop 15");
        lbl = new JLabel("Your notifications and history");
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
        clearButton.addActionListener(contr.new clearLogListener(log));
        clearButton.setFont(Plain);
        add(clearButton, "span 2, gaptop 10");     
        columnNames = new String[5];
        columnNames[0] = "Name";
        columnNames[1] = "Description";
        columnNames[2] = "Price";
        columnNames[3] = "Seller";
        columnNames[4] = "Buyer"; 
        String[][] soldData = new String[0][0];
        lbl = new JLabel("Items you have sold: ");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        soldModel = new DefaultTableModel(soldData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable soldTable = new JTable(soldModel);
        soldTable.setRowHeight(20);
        soldTable.setFont(Plain);        
        soldTable.getTableHeader().setFont(PBold);
        add(new JScrollPane(soldTable), "span 2");  
        lbl = new JLabel("Items you have bought: ");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        String[][] boughtData = new String[0][0];
        boughtModel = new DefaultTableModel(boughtData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable boughtTable = new JTable(boughtModel);
        boughtTable.setRowHeight(20);
        boughtTable.setFont(Plain);        
        boughtTable.getTableHeader().setFont(PBold);
        add(new JScrollPane(boughtTable), "span 2");
    }
    public void updateLog(ArrayList<String> logList){
        log.setText("");
       for(String s : logList){
           log.setText(log.getText() + s + "\n");
       }
       repaint();
       revalidate();
    }
    
    public void updateBought(ArrayList<SoldItem> items){
        String[][] rowData = new String[items.size()][5];
        try{
            for(int i = 0; i <  items.size(); i++)
            {
                SoldItem item = items.get(i);
                rowData[i][0] = item.getItem().getName();
                rowData[i][1] = item.getItem().getDescription();
                rowData[i][2] = Float.toString(item.getItem().getPrice());
                rowData[i][3] = item.getSeller();
                rowData[i][4] = item.getBuyer();
            }
            boughtModel.setDataVector(rowData, columnNames);
        }
        catch(RemoteException e){
            e.printStackTrace();
            contr.remoteExceptionHandler(e);
        }
        repaint();
        revalidate();
    }
    
    public void updateSold(ArrayList<SoldItem> items){
        String[][] rowData = new String[items.size()][5];
        try{
            for(int i = 0; i <  items.size(); i++)
            {
                SoldItem item = items.get(i);
                rowData[i][0] = item.getItem().getName();
                rowData[i][1] = item.getItem().getDescription();
                rowData[i][2] = Float.toString(item.getItem().getPrice());
                rowData[i][3] = item.getSeller();
                rowData[i][4] = item.getBuyer();
            }
            soldModel.setDataVector(rowData, columnNames);
        }
        catch(RemoteException e){
            e.printStackTrace();
            contr.remoteExceptionHandler(e);
        }
        repaint();
        revalidate();
    }
}