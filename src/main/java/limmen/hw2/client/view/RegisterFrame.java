/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class RegisterFrame extends JFrame {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    public RegisterFrame(GuiController contr){
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 2 ID2212 | Register");
        this.setContentPane(new JScrollPane(new RegisterPanel()));
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
    
    private class RegisterPanel extends JPanel{
        private JTextField nameField;
        RegisterPanel(){
            setLayout(new MigLayout("wrap 2, insets 50 50 50 50"));  //insets T, L, B, R
            JLabel lbl;
            lbl = new JLabel("<html> Welcome to the marketplace. <br> <br>"
                    + "To be able to buy/sell items you need to be registered <br>"
                    + "and have a bankaccount. <br>"
                    + "Register below and we'll set up a bankaccount for you<br> </html>");
            lbl.setFont(Title);
            add(lbl, "span 2");
            lbl = new JLabel("Username: ");
            lbl.setFont(PBold);
            add(lbl, "span 1, gaptop 20");
            nameField = new JTextField(25);
            nameField.setFont(Plain);
            add(nameField, "span 1, gaptop 20");            
            JButton registerButton = new JButton("Register");
            registerButton.setFont(Title);
            registerButton.addActionListener(contr.new RegisterListener(nameField));
            add(registerButton, "span 2, gaptop 15");
        }
    }
}
