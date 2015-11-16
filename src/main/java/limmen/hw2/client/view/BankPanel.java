/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Font;
import java.rmi.RemoteException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class BankPanel extends JPanel{
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    GuiController contr;
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
        lbl = new JLabel(Float.toString(contr.getClient().getAccount().getBalance()));        
        lbl.setFont(Plain); 
        add(lbl, "span 1");
    }
}
