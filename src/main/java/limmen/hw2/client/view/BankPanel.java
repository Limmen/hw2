/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Font;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Bank-panel
 * @author kim
 */
public class BankPanel extends JPanel{
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    private final JLabel balance;
    public BankPanel(GuiController contr) throws RemoteException, NullPointerException{
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("Bankaccount for user: " + contr.getClient().getName());
        lbl.setFont(Title);
        add(lbl, "span 2");
        lbl = new JLabel("Bank: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        lbl = new JLabel(contr.getBankName());
        lbl.setFont(Plain);
        add(lbl, "span 1");
        lbl = new JLabel("Balance: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        balance = new JLabel(Float.toString(contr.getClient().getAccount().getBalance()));
        balance.setFont(Plain);
        add(balance, "span 1");
        JButton depositButton = new JButton("deposit");
        depositButton.setFont(Plain);
        add(depositButton, "span  1, gaptop 30");
        JTextField depositField = new JTextField(25);
        depositField.setFont(Plain);
        depositButton.addActionListener(contr.new depositListener(depositField));
        add(depositField, "span 1, gaptop 30");
        JButton withdrawButton = new JButton("withdraw");
        withdrawButton.setFont(Plain);
        add(withdrawButton, "span  1, gaptop 10");
        JTextField withdrawField = new JTextField(25);
        withdrawField.setFont(Plain);
        withdrawButton.addActionListener(contr.new withdrawListener(withdrawField));
        add(withdrawField, "span 1, gaptop 30");
    }
    public void updateBalance(){
        try{
            balance.setText(Float.toString(contr.getClient().getAccount().getBalance()));
            repaint();
            revalidate();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
