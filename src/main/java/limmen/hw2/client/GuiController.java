/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author kim
 */
public class GuiController {
    private final GuiController contr = this;
    private RegisterFrame registerFrame;
    private MainFrame mainFrame;
    public GuiController(){
        registerFrame = new RegisterFrame(contr);
    }
    public static void main(String[] args){
        new GuiController();
    }
    
    class RegisterListener implements ActionListener {
        private final JTextField nameField;
        
        RegisterListener(JTextField nameField){
            this.nameField = nameField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0){
                //               new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.GUESS, guessField.getText()), contr).execute();
                registerFrame.setVisible(false);
                mainFrame = new MainFrame(contr, (Client) new ClientImpl(nameField.getText()));
            }
            else
                JOptionPane.showMessageDialog(null, "Your  username needs is"
                        + " to short or contains invalid characters",
                        "Invalid guess", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
        }
    }
    
    class DeRegisterListener implements ActionListener {
        
        DeRegisterListener(){
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}
